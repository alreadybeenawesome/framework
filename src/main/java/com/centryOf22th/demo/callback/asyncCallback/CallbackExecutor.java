package com.centryOf22th.demo.callback.asyncCallback;

/**
 * Created by louis on 16-11-4.
 */
public interface CallbackExecutor {


    /**
     * 回调的时候调用
     * @param objects  将处理后的结果作为参数返回给回调方法
     */
    void callback(Object ...objects);
}
