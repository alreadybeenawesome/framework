package com.centryOf22th.framework.orm.assist;

import com.centryOf22th.framework.exception.WithoutAnnotationException;
import com.centryOf22th.framework.orm.metadata.ColumnMetaData;
import com.centryOf22th.framework.orm.metadata.TableMetaData;
import com.centryOf22th.framework.utils.DateUtil;
import com.centryOf22th.framework.wsClient.useJaeeApi.Run;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by louis on 16-10-27.
 */
public class MysqlAssistant extends DbAssistant {
    private static Logger logger = LogManager.getLogger(MysqlAssistant.class);


    @Override
    public void resolveInsertSql(TableMetaData tableMetaData) {
        List list = tableMetaData.getColumnMetaDatas();
        if(list.size()==0){
            throw new RuntimeException("the entity of "+tableMetaData.getClass()+" don't have field or these have not been initialized");
        }
        SqlAssistant sqlAssist = new SqlAssistant();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableMetaData.getTableName())
                .append("(");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ColumnMetaData columnMetaData = (ColumnMetaData)iterator.next();
            sql.append(columnMetaData.getColumnName()).append(",");
        }
        sql.delete(sql.lastIndexOf(","), sql.length());
        sql.append(")").append(" values ").append("(");
        iterator = list.iterator();
        while (iterator.hasNext()) {
            ColumnMetaData columnMetaData = (ColumnMetaData)iterator.next();
            if (!columnMetaData.hasIdentity()) {
                sql.append("?").append(",");
            }
        }
        sql.delete(sql.lastIndexOf(","), sql.length()).append(")");
        sqlAssist.setSql(sql.toString());
        tableMetaData.setDefaultInsertSql(sqlAssist);
    }

    @Override
    public Object[] generateInsertValues (Object obj,TableMetaData tableMetaData) {
        List list = tableMetaData.getColumnMetaDatas();
        ArrayList<Object> values = new ArrayList<>();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ColumnMetaData columnMetaData = (ColumnMetaData) iterator.next();
            try {
                Object value = null;
                if (!columnMetaData.hasIdentity()) {
                    String propertyName = columnMetaData.getPropertyName();
                    switch (propertyName) {
                        case "createUser":
                            break;
                        case "updateUser":
                            break;
                        case "createTime":
                            value = DateUtil.getCurrentTimeWithDefaultPattern();
                            columnMetaData.getPropertySetM().invoke(obj, value);
                            break;
                        case "updateTime":
                            value = DateUtil.getCurrentTimeWithDefaultPattern();
                            columnMetaData.getPropertySetM().invoke(obj, value);
                            break;
                        case "deleted":
                            break;
                        default:
                            value=columnMetaData.getPropertyGetM().invoke(obj);

                    }
                }
                if (value != null) {
                    values.add(value);
                }
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }

        }
        return values.toArray();
    }

    @Override
    public Object[] generateUpdateValues(Object obj, TableMetaData tableMetaData) {
        List list =tableMetaData.getColumnMetaDatas();
        Object primaryKey =null;
        try {
            primaryKey =tableMetaData.getMethodOfGetWithPrimaryKey().invoke(obj);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        if(primaryKey==null){
            logger.error(obj.getClass().getName()+" must choose one field be the primary key and this field must be initialized or may due to didn't config with @PrimaryKey annotation at the top of get method");
            throw new WithoutAnnotationException(obj.getClass().getName()+" must choose one field be the primary key and this field must be initialized or may due to didn't config with @PrimaryKey annotation at the top of get method");
        }
        ArrayList<Object> values=new ArrayList<>();
        Iterator iterator =list.iterator();
        while(iterator.hasNext()){
            Object value =null;
            try {
                ColumnMetaData columnMetaData =(ColumnMetaData)iterator.next();
                if (!columnMetaData.hasPrimaryKey()) {
                    String propertyName = columnMetaData.getPropertyName();
                    switch (propertyName) {
                        case "createUser":
                            break;
                        case "updateUser":
                            break;
                        case "createTime":
                            value = DateUtil.getCurrentTimeWithDefaultPattern();
                            columnMetaData.getPropertySetM().invoke(obj);
                            break;
                        case "updateTime":
                            value = DateUtil.getCurrentTimeWithDefaultPattern();
                            columnMetaData.getPropertySetM().invoke(obj);
                            break;
                        case "deleted":
                            break;
                        default:
                            value=columnMetaData.getPropertyGetM().invoke(obj);

                    }
                }
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
            if(value!=null){
                values.add(value);
            }
        }
        values.add(primaryKey);
        return values.toArray();
    }

    @Override
    public String generateLockSql(Class clzss) {
        return null;
    }

    @Override
    public String generatePageSql(String val1, int val2, int val3, int val4, int val5) {
        return null;
    }


}
