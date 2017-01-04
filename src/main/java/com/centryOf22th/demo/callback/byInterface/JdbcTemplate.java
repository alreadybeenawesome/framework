package com.centryOf22th.demo.callback.byInterface;

/**
 * Created by louis on 16-11-4.
 */
public class JdbcTemplate {
    public void execute(CRUDCallBack crudCallBack) {
        getConnection();
        crudCallBack.doCURD();
        releaseConnection();
    }

    public <T> T query() {
        Object o =new Object();
        execute(new CRUDCallBack() {
            @Override
            public void doCURD() {
                System.out.println("select * from tableName");
            }
        });
        return (T)o;
    }


    public void getConnection() {
        System.out.println("retrieve a connection here");
    }

    public void releaseConnection() {
        System.out.println("release a connection here");
    }
}
