package com.centryOf22th.demo.callback.asyncCallback;

/**
 * Created by louis on 16-11-4.
 */

/**
 * 这个类相当于你的同学
 */
public class Remote {


    /**
     * 处理消息
     */
    public void messagesHandler(String message,CallbackExecutor callbackExecutor){
        /**模拟远程类正在处理其他事情，可能需要花费许多时间**/
        for(int i=0;i<1000000000;i++)
        {

        }
        /**处理完其他事情，现在来处理消息**/
        System.out.println("===="+message);
        System.out.println("I have executed the message by Local");
        /**执行回调**/
        callbackExecutor.callback(new Object[]{"Nice to meet you~!"});  //这相当于同学执行完之后打电话给你
    }
}
