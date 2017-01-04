package com.centryOf22th.framework.orm.metadata;

import java.lang.reflect.Method;

/**
 * Created by louis on 16-10-21.
 */
public class FieldMetaData {

    private String fieldName;  //变量名
    private Class fieldType;  //变量类型
    private Method filedSetMethod;  //变量的set方法
    private Method filedGetMethod;  //变量的get方法


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class fieldType) {
        this.fieldType = fieldType;
    }

    public Method getFiledSetMethod() {
        return filedSetMethod;
    }

    public void setFiledSetMethod(Method filedSetMethod) {
        this.filedSetMethod = filedSetMethod;
    }

    public Method getFiledGetMethod() {
        return filedGetMethod;
    }

    public void setFiledGetMethod(Method filedGetMethod) {
        this.filedGetMethod = filedGetMethod;
    }
}
