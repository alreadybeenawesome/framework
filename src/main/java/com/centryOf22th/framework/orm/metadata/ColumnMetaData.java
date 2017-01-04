package com.centryOf22th.framework.orm.metadata;

import java.lang.reflect.Method;

/**
 * Created by louis on 16-10-24.
 */
public class ColumnMetaData {

    private String propertyName;
    private String columnName;
    private String propertyTypeName;
    private String propSetMethodName;
    private String propGetMethodName;
    private Method propertySetM;
    private Method propertyGetM;
    private boolean hasIdentity;  //该列是否顶了@identity注解
    private boolean hasPrimaryKey; //该列是否定了@PrimaryKey注解

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getPropSetMethodName() {
        return propSetMethodName;
    }

    public void setPropSetMethodName(String propSetMethodName) {
        this.propSetMethodName = propSetMethodName;
    }

    public String getPropGetMethodName() {
        return propGetMethodName;
    }

    public void setPropGetMethodName(String propGetMethodName) {
        this.propGetMethodName = propGetMethodName;
    }

    public Method getPropertySetM() {
        return propertySetM;
    }

    public void setPropertySetM(Method propertySetM) {
        this.propertySetM = propertySetM;
    }

    public Method getPropertyGetM() {
        return propertyGetM;
    }

    public void setPropertyGetM(Method propertyGetM) {
        this.propertyGetM = propertyGetM;
    }

    public boolean hasIdentity() {
        return hasIdentity;
    }

    public void setHasIdentity(boolean hasIdentity) {
        this.hasIdentity = hasIdentity;
    }

    public boolean hasPrimaryKey() {
        return hasPrimaryKey;
    }

    public void setHasPrimaryKey(boolean hasPrimaryKey) {
        this.hasPrimaryKey = hasPrimaryKey;
    }
}
