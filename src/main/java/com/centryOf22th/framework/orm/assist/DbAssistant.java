package com.centryOf22th.framework.orm.assist;

import com.centryOf22th.framework.exception.WithoutAnnotationException;
import com.centryOf22th.framework.model.Student;
import com.centryOf22th.framework.orm.metadata.ColumnMetaData;
import com.centryOf22th.framework.orm.metadata.FieldMetaData;
import com.centryOf22th.framework.orm.metadata.TableMetaData;
import com.centryOf22th.framework.orm.model.Identity;
import com.centryOf22th.framework.orm.model.PrimaryKey;
import com.centryOf22th.framework.utils.AssertUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.persistence.Column;
import javax.persistence.Table;
import java.awt.image.ShortLookupTable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by louis on 16-10-24.
 */
public abstract class DbAssistant {

    private static Logger logger = LogManager.getLogger(DbAssistant.class);
    private static final Map<Class<?>, TableMetaData> tableMapCache = new ConcurrentHashMap<>();
    private static final List<String> methodNamesOfObject = new ArrayList<>();
    private static final Map<String, Object> primitiveType = new HashMap<>();

    static {
        final Method[] oMethods = Object.class.getMethods();
        for (Method method : oMethods) {
            methodNamesOfObject.add(method.getName());
        }

    }

    public DbAssistant() {
        primitiveType.put("boolean", Boolean.class.getName());
        primitiveType.put("byte", Byte.class.getName());
        primitiveType.put("short", Short.class.getName());
        primitiveType.put("int", Integer.class.getName());
        primitiveType.put("long", Long.class.getName());
        primitiveType.put("float", Float.class.getName());
        primitiveType.put("double", Double.class.getName());
    }


    //mysql和oracle有不同的insertSql 靠子类去实现
    public abstract void resolveInsertSql(TableMetaData tableMetaData);

    public abstract Object[] generateInsertValues(Object obj, TableMetaData tableMetaData);

    public abstract Object[] generateUpdateValues(Object obj, TableMetaData tableMetaData);

    public abstract String generatePageSql(String val1, int val2, int val3, int val4, int val5);

    public abstract String generateLockSql(Class clzss);

    public String generateSingleObjectQuerySql(TableMetaData tableMetaData) {
//        Table table =(Table) clzss.getAnnotation(Table.class);
//        AssertUtil.isWithAnnotation(table,clzss);
//        String sql ="select * from "+table.schema()+" where "
        AssertUtil.isWithAnnotation(tableMetaData.getTableName(), tableMetaData.getEntityClass());
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ")
                .append(tableMetaData.getTableName())
                .append(" where ")
                .append(tableMetaData.getPrimaryKeyColumn())
                .append(" = ")
                .append("?");
        return sql.toString();
    }

    /**
     * @param obj the model entity
     * @return
     */
    public TableMetaData resolveTable(Object obj) throws Exception {
        return resolveTable(obj.getClass(), obj);
    }


    public TableMetaData resolveTable(Class clzss) throws Exception {
        return resolveTable(clzss, null);
    }

    protected TableMetaData resolveTable(Class<?> clzss, Object obj) throws Exception {
        if (tableMapCache.containsKey(clzss)) {
            return tableMapCache.get(clzss);
        } else {
            TableMetaData tableMetaData = null;
            Table table = (Table) clzss.getAnnotation(Table.class);
            //获取这个实体类的所有方法
            Method methods[] = clzss.getMethods();
            if (methods == null || methods.length == 0) {
                logger.error(clzss.getName() + " hasn't any of methods");
                throw new RuntimeException(clzss.getName() + " hasn't any of methods");
            }
            tableMetaData = new TableMetaData();
            if (table == null) {
                logger.error(clzss.getName() + " can not without @table annotation at the top of class");
                throw new WithoutAnnotationException(clzss.getName() + " can not without @table annotation at the top of class");
            }

            tableMetaData.setTableName(table.schema());
            tableMetaData.setEntityClass(clzss);
            ArrayList<ColumnMetaData> columnsOfMetadata = new ArrayList<>();
            Map<String, ColumnMetaData> columnMetaDataWithPropertyNameMap = new HashMap<>();
            Map<String, ColumnMetaData> columnMetaDataMap = new HashMap<>();
            HashMap<String, FieldMetaData> fieldMetaDataMap = new HashMap<>();

            //遍历这个该Model类的所有方法
            for (int i = 0; i < methods.length; i++) {
                Method m = methods[i];
                //过滤掉Object的方法
                if (methodNamesOfObject.contains(m.getName())) {
                    continue;
                }
                try {
                    ColumnMetaData columnMetaData = this.resolveColumn(obj, clzss, m, fieldMetaDataMap);
                    if (columnMetaData != null) {
                        if (columnMetaData.hasIdentity()) {
                            tableMetaData.setHasIdentity(true);
                            tableMetaData.setMethodOfGetWithIdentity(columnMetaData.getPropertyGetM());
                            tableMetaData.setMethodOfSetWithIdentity(columnMetaData.getPropertySetM());
                        }
                        if (columnMetaData.hasPrimaryKey()) {
                            tableMetaData.setHasPrimaryKey(true);
                            tableMetaData.setMethodOfGetWithPrimaryKey(columnMetaData.getPropertyGetM());
                            tableMetaData.setMethodOfSetWithPrimaryKey(columnMetaData.getPropertySetM());
                            tableMetaData.setPrimaryKeyColumn(columnMetaData.getColumnName());
                        }
                        columnsOfMetadata.add(columnMetaData);
                        columnMetaDataMap.put(columnMetaData.getColumnName(), columnMetaData);
                        columnMetaDataWithPropertyNameMap.put(columnMetaData.getPropertyName(), columnMetaData);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            tableMetaData.setColumnMetaDatas(columnsOfMetadata);
            tableMetaData.setColumnMetaDataMap(columnMetaDataMap);
            tableMetaData.setColumnMetaDataWithPropertyNameMap(columnMetaDataWithPropertyNameMap);
            tableMetaData.setFieldMetaDataMapWithNoColumnAnno(fieldMetaDataMap);
            this.resolveInsertSql(tableMetaData);
            this.resolveUpdateSql(tableMetaData);
            this.resolveLogicDeleteSql(tableMetaData);
            this.resolveDeleteSql(tableMetaData);

            return tableMetaData;
        }
    }

    private void resolveLogicDeleteSql(TableMetaData tableMetaData) {
        if (tableMetaData.isWithIdentity() && tableMetaData.isHasParentModel()) {
            StringBuilder sql = new StringBuilder();
            SqlAssistant sqlAssist = new SqlAssistant();
            sql.append("update ").append(tableMetaData.getTableName()).append(" set deleted = 1")
                    .append(" where ").append(tableMetaData.getPrimaryKeyColumn()).append(" = ").append("?");
            sqlAssist.setSql(sql.toString());
            sqlAssist.setIdentity(true);
            tableMetaData.setDefaultLogicDeleteSql(sqlAssist);
        }
    }

    private void resolveDeleteSql(TableMetaData tableMetaData) {
        if (tableMetaData.isWithIdentity()) {
            StringBuilder sql = new StringBuilder();
            sql.append("delete from ")
                    .append(tableMetaData.getTableName())
                    .append(" where ")
                    .append(tableMetaData.getPrimaryKeyColumn())
                    .append("=")
                    .append("?");
            SqlAssistant sqlAssist = new SqlAssistant();
            sqlAssist.setIdentity(true);
            sqlAssist.setSql(sql.toString());
            tableMetaData.setDefaultDeleteSql(sqlAssist);
        }

    }

    private ColumnMetaData resolveColumn(Object obj, Class<?> clzss, Method m, HashMap<String, FieldMetaData> fieldMetaDataMap) throws Exception {
        Column column = m.getAnnotation(Column.class);
        Identity identity = m.getAnnotation(Identity.class);
        PrimaryKey primaryKey = m.getAnnotation(PrimaryKey.class);

        //m的方法简单名字
        String mName = m.getName();
        //根据set方法获取成员变量名称
        if (!mName.startsWith("get")) {
            return null;

        } else {
            //clzss类的成员变量名
            String fieldName = mName.substring(3, 4).toLowerCase() + mName.substring(4);
            String setMethodName = "set" + mName.substring(3);
            //根据成员变量名称拿到该成员变量的Field
            Field field = null;
            Class superClass = clzss;
            //这里是获取全限定名                 //java.lang.object
            while (!superClass.getName().equals(Object.class.getName()) && field == null) {
                try {
                    //获取对应成员变量名字的Field对象(不分修饰符)
                    field = clzss.getDeclaredField(fieldName); //获取所有修饰符修饰的field
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } finally {
                    //获取超类的class
                    superClass = superClass.getSuperclass();
                }
            }
            if (field == null) {
                return null;
            } else {

                if (column == null) {
                    throw new WithoutAnnotationException("must config with @Column annotation at the top of the method of " + mName);
                }
                //获取指定方法需要传入方法简单名称,方法入参的入参类型
                Method setMethod = clzss.getMethod(setMethodName, new Class[]{field.getType()});
                if (setMethod == null) {
                    return null;
                } else if (!field.getType().getName().contains("java.") && !primitiveType.containsKey(field.getType().getName())) {
                    FieldMetaData fieldMetaData = new FieldMetaData();
                    fieldMetaData.setFieldName(fieldName);
                    fieldMetaData.setFieldType(field.getType());
                    fieldMetaData.setFiledGetMethod(m);
                    fieldMetaData.setFiledSetMethod(setMethod);
                    fieldMetaDataMap.put(field.getName(), fieldMetaData);
                    return null;
                } else {
                    if (obj != null) {
                        if (m.invoke(obj) == null) {
                            return null;
                        }
                    }

                    ColumnMetaData columnMetaData = new ColumnMetaData();
                    columnMetaData.setPropertyName(field.getName());
                    columnMetaData.setPropertyTypeName(field.getType().getName());
                    columnMetaData.setColumnName(column.name());
                    columnMetaData.setPropSetMethodName(setMethodName);
                    columnMetaData.setPropGetMethodName(mName);
                    columnMetaData.setPropertyGetM(m);
                    columnMetaData.setPropertySetM(setMethod);
                    columnMetaData.setHasIdentity(identity != null);
                    columnMetaData.setHasPrimaryKey(primaryKey != null);
                    return columnMetaData;

                }
            }
        }
    }


    protected void resolveUpdateSql(TableMetaData tableMetaData) {
        //update sql 需要提供主键
        if (tableMetaData.isWithPrimaryKey()) {
            List list = tableMetaData.getColumnMetaDatas();
            StringBuilder sql = new StringBuilder();
            SqlAssistant sqlAssist = new SqlAssistant();
            sql.append("update ").append(tableMetaData.getTableName()).append(" set ");
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                ColumnMetaData columnMetaData = (ColumnMetaData) iterator.next();
                if (!columnMetaData.hasPrimaryKey()) {
                    sql.append(columnMetaData.getColumnName()).append("=").append("?").append(",");
                }
            }
            sql.delete(sql.lastIndexOf(","), sql.length()).append(" where ").append(tableMetaData.getPrimaryKeyColumn()).append("=").append("?");
            sqlAssist.setSql(sql.toString());
            tableMetaData.setDefaultUpdateSql(sqlAssist);


        }
    }

    public static void main(String[] args) {
//        Object o = new Object();
//        System.out.println(o.getClass().getSuperclass());
//        TableMetaData t = new TableMetaData();
//
//        ArrayList arrayList = new ArrayList();
//        System.out.println(Boolean.class.getName());
//        System.out.println(arrayList.getClass().getSuperclass());
//
//        final Method[] oMethods = Object.class.getMethods();
//        for(Method method :oMethods){
//            System.out.println(method.getName());
//            methodNamesOfObject.add(method.getName());
//        }

        System.out.println(Object.class.getClass());

    }

    public static void resolvePrepareStatement(PreparedStatement ps, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value == null) {
                ps.setObject(i + 1, null);
            } else {
                String cNameOfValue = value.getClass().getName();
                switch (cNameOfValue) {
                    case "java.lang.Integer":
                        ps.setInt(i + 1, (Integer) value);
                        break;
                    case "java.math.BigDecimal":
                        ps.setBigDecimal(i + 1, (BigDecimal) value);
                        break;
                    case "java.lang.Float":
                        ps.setFloat(i + 1, (Float) value);
                        break;
                    case "java.lang.Double":
                        ps.setDouble(i + 1, (Double) value);
                        break;
                    case "java.lang.Short":
                        ps.setShort(i + 1, (Short) value);
                        break;
                    case "java.util.Date":
                        ps.setDate(i + 1, new java.sql.Date(((Date) value).getTime()));
//                        ps.setTimestamp(i+1,new Timestamp(((Date)value).getTime()));
                        break;
                    case "java.lang.Boolean":
                        ps.setBoolean(i + 1, (Boolean) value);
                        break;
                    case "java.lang.Byte":
                        ps.setByte(i + 1, (Byte) value);
                        break;
                    case "java.lang.String":
                        ps.setString(i + 1, (String) value);
                        break;
                    case "java.lang.Long":
                        ps.setLong(i + 1, (Long) value);
                        break;
                }
            }


        }
    }


    public void resolveIdentityKey(TableMetaData tableMetaData, Object t, KeyHolder keyHolder) {

        //getName() will return a full of class name
        String returnType = tableMetaData.getMethodOfGetWithIdentity().getReturnType().getName();
        try {
            switch (returnType) {
                case "java.lang.Integer":
                    tableMetaData.getMethodOfSetWithIdentity().invoke(t, new Object[]{Integer.valueOf(keyHolder.getKey().intValue())});
                    break;
                case "java.lang.Long":
                    tableMetaData.getMethodOfSetWithIdentity().invoke(t, new Object[]{Long.valueOf(keyHolder.getKey().longValue())});
                    break;
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }


    @SuppressWarnings("all")
    public static <T> T resolveResultSet(Class actualTypeOfParamT, ResultSet rs, TableMetaData tableMetadata) {
        Object  resultObj=null;
        try {
             resultObj =actualTypeOfParamT.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, ColumnMetaData> propertyMap = tableMetadata.getColumnMetaDataWithPropertyNameMap();
        if(propertyMap==null||propertyMap.size()==0){
            return (T)resultObj;
        }
//        Map columnsMap =tableMetadata.getColumnMetaDataMap();
        Set entrySet = propertyMap.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            ColumnMetaData columnMetadata = propertyMap.get(key);
            try {
                Object value = null;
                switch (columnMetadata.getPropertyTypeName()) {
                    case "java.lang.Integer":
                        value = rs.getInt(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.math.BigDecimal":
                        value = rs.getBigDecimal(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.Float":
                        value = rs.getFloat(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.Double":
                        value = rs.getDouble(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.Short":
                        value = rs.getShort(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.util.Date":
                        value = rs.getDate(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.Boolean":
                        value = rs.getBoolean(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.Byte":
                        value = rs.getByte(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.String":
                        value = rs.getString(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.lang.Long":
                        value = rs.getLong(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    case "java.sql.Timestamp":
                        value = rs.getTimestamp(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                        break;
                    default:
                        value = rs.getObject(columnMetadata.getColumnName());
                        columnMetadata.getPropertySetM().invoke(resultObj,new Object[]{value});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return (T)resultObj;
    }
}
