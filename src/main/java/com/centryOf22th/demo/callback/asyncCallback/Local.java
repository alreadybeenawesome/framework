package com.centryOf22th.demo.callback.asyncCallback;

/**
 * Created by louis on 16-11-4.
 */


/**
 * local类相当于你自己
 */
public class Local implements CallbackExecutor, Runnable {

    private Remote remote;

    private String messages;  //要发出去的消息

    public Local(Remote remote, String messages) {
        this.remote = remote;
        this.messages = messages;
    }

    /**
     * 发送消息
     */
    public void sendMessages() {
        System.out.println("sender message thread :" + Thread.currentThread().getName());
        //create a new thread to send message
        Thread thread = new Thread(this,"sender");
        thread.start();
        System.out.println("message has been sent by Local"+"===="+Thread.currentThread().getName());
    }

    //发送消息之后,接受到消息要做的事情在这里做
    @Override
    public void callback(Object... objects) {

        //打印返回的消息
        System.out.println("返回的消息:"+objects[0]);
        System.out.println("发送消息的线程名称:"+Thread.currentThread().getName());
        //中断发送消息线程
        Thread.interrupted();
    }

    @Override
    public void run() {
        remote.messagesHandler(messages,this);
    }

    public static void main(String[] args) {
        Local local = new Local(new Remote(),"Hello");

        local.sendMessages();
    }
}
