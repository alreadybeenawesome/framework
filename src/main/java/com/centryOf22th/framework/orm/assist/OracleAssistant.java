package com.centryOf22th.framework.orm.assist;

import com.centryOf22th.framework.orm.metadata.TableMetaData;

/**
 * Created by louis on 16-10-27.
 */
public class OracleAssistant extends DbAssistant {

    @Override
    public void resolveInsertSql(TableMetaData tableMetaData) {

    }

    @Override
    public Object[] generateUpdateValues(Object obj, TableMetaData tableMetaData) {
        return new Object[0];
    }

    @Override
    public Object[] generateInsertValues(Object obj,TableMetaData tableMetaData) {
        return new Object[0];
    }

    @Override
    public String generatePageSql(String val1, int val2, int val3, int val4, int val5) {
        return null;
    }

    @Override
    public String generateLockSql(Class clzss) {
        return null;
    }


}
