package com.centryOf22th.framework.baseDao;

/**
 * Created by louis on 16-10-27.
 */
public interface BaseDao<T> {


    void save(T t);

    void update(T t);

    T get(Object o);

}
