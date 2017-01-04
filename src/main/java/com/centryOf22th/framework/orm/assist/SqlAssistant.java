package com.centryOf22th.framework.orm.assist;

/**
 * Created by louis on 16-10-24.
 */
public class SqlAssistant {
    private String sql ;   //sql语句
    private Object[] values;
    private boolean identity; //是否启用identity这种自增主键的方式

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }
}
