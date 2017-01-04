package com.centryOf22th.framework.orm.metadata;

import com.centryOf22th.framework.orm.assist.SqlAssistant;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louis on 16-10-21.
 */
public class TableMetaData {

    private String tableName;         //表名
    private Class entityClass;        //实体类
    private boolean hasIdentity=false;//是否为identity
    private boolean hasPrimaryKey;    //是否为主键
    private String primaryKeyColumn;  //主键列名
    private Method methodOfGetWithIdentity;      //标识@identity注解的Get方法
    private Method methodOfSetWithIdentity;      //标识@identity注解的Set方法
    private Method methodOfGetWithPrimaryKey;    //标识@primaryKey注解的Get方法
    private Method methodOfSetWithPrimaryKey;    //标识@primaryKey注解的Get方法
    private SqlAssistant defaultInsertSql;
    private SqlAssistant defaultUpdateSql;
    private SqlAssistant defaultDeleteSql;
    private SqlAssistant defaultLogicDeleteSql;
    private boolean hasParentModel;

    private Map<String,ColumnMetaData> columnMetaDataWithPropertyNameMap=new HashMap<>();
    private Map<String,ColumnMetaData> columnMetaDataMap=new HashMap<>();
    private List<ColumnMetaData> columnMetaDatas =new ArrayList<>();
    private Map<String,FieldMetaData> fieldMetaDataMap =new HashMap<>();
    private Map<String, FieldMetaData> fieldMetaDataMapWithNoColumnAnno = new HashMap();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public boolean isWithIdentity() {
        return hasIdentity;
    }

    public void setHasIdentity(boolean hasIdentity) {
        this.hasIdentity = hasIdentity;
    }

    public boolean isWithPrimaryKey() {
        return hasPrimaryKey;
    }

    public void setHasPrimaryKey(boolean hasPrimaryKey) {
        this.hasPrimaryKey = hasPrimaryKey;
    }

    public String getPrimaryKeyColumn() {
        return primaryKeyColumn;
    }

    public void setPrimaryKeyColumn(String primaryKeyColumn) {
        this.primaryKeyColumn = primaryKeyColumn;
    }

    public Method getMethodOfGetWithIdentity() {
        return methodOfGetWithIdentity;
    }

    public void setMethodOfGetWithIdentity(Method methodOfGetWithIdentity) {
        this.methodOfGetWithIdentity = methodOfGetWithIdentity;
    }

    public Method getMethodOfSetWithIdentity() {
        return methodOfSetWithIdentity;
    }

    public void setMethodOfSetWithIdentity(Method methodOfSetWithIdentity) {
        this.methodOfSetWithIdentity = methodOfSetWithIdentity;
    }

    public Method getMethodOfGetWithPrimaryKey() {
        return methodOfGetWithPrimaryKey;
    }

    public void setMethodOfGetWithPrimaryKey(Method methodOfGetWithPrimaryKey) {
        this.methodOfGetWithPrimaryKey = methodOfGetWithPrimaryKey;
    }

    public Method getMethodOfSetWithPrimaryKey() {
        return methodOfSetWithPrimaryKey;
    }

    public void setMethodOfSetWithPrimaryKey(Method methodOfSetWithPrimaryKey) {
        this.methodOfSetWithPrimaryKey = methodOfSetWithPrimaryKey;
    }

    public SqlAssistant getDefaultInsertSql() {
        return defaultInsertSql;
    }

    public void setDefaultInsertSql(SqlAssistant defaultInsertSql) {
        this.defaultInsertSql = defaultInsertSql;
    }

    public SqlAssistant getDefaultUpdateSql() {
        return defaultUpdateSql;
    }

    public void setDefaultUpdateSql(SqlAssistant defaultUpdateSql) {
        this.defaultUpdateSql = defaultUpdateSql;
    }

    public SqlAssistant getDefaultDeleteSql() {
        return defaultDeleteSql;
    }

    public void setDefaultDeleteSql(SqlAssistant defaultDeleteSql) {
        this.defaultDeleteSql = defaultDeleteSql;
    }

    public SqlAssistant getDefaultLogicDeleteSql() {
        return defaultLogicDeleteSql;
    }

    public void setDefaultLogicDeleteSql(SqlAssistant defaultLogicDeleteSql) {
        this.defaultLogicDeleteSql = defaultLogicDeleteSql;
    }

    public boolean isHasParentModel() {
        return hasParentModel;
    }

    public void setHasParentModel(boolean hasParentModel) {
        this.hasParentModel = hasParentModel;
    }

    public Map<String, ColumnMetaData> getColumnMetaDataWithPropertyNameMap() {
        return columnMetaDataWithPropertyNameMap;
    }

    public void setColumnMetaDataWithPropertyNameMap(Map<String, ColumnMetaData> columnMetaDataWithPropertyNameMap) {
        this.columnMetaDataWithPropertyNameMap = columnMetaDataWithPropertyNameMap;
    }

    public Map<String, ColumnMetaData> getColumnMetaDataMap() {
        return columnMetaDataMap;
    }

    public void setColumnMetaDataMap(Map<String, ColumnMetaData> columnMetaDataMap) {
        this.columnMetaDataMap = columnMetaDataMap;
    }

    public List<ColumnMetaData> getColumnMetaDatas() {
        return columnMetaDatas;
    }

    public void setColumnMetaDatas(List<ColumnMetaData> columnMetaDatas) {
        this.columnMetaDatas = columnMetaDatas;
    }

    public Map<String, FieldMetaData> getFieldMetaDataMap() {
        return fieldMetaDataMap;
    }

    public void setFieldMetaDataMap(Map<String, FieldMetaData> fieldMetaDataMap) {
        this.fieldMetaDataMap = fieldMetaDataMap;
    }



    public Map<String, FieldMetaData> getFieldMetaDataMapWithNoColumnAnno() {
        return fieldMetaDataMapWithNoColumnAnno;
    }

    public void setFieldMetaDataMapWithNoColumnAnno(Map<String, FieldMetaData> fieldMetaDataMapWithNoColumnAnno) {
        this.fieldMetaDataMapWithNoColumnAnno = fieldMetaDataMapWithNoColumnAnno;
    }
}
