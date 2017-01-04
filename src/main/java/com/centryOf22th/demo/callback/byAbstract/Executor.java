package com.centryOf22th.demo.callback.byAbstract;

/**
 * Created by louis on 16-11-4.
 */
public abstract class Executor {

    public void execute(){
        getConnection();
        doCRUD();
        releaseConnection();
    }

    public void getConnection(){
        System.out.println("retrieve a connection here");
    }

    public void releaseConnection(){
        System.out.println("release a connection here");
    }



    public abstract void doCRUD();





}
