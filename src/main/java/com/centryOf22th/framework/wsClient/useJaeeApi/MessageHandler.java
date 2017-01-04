package com.centryOf22th.framework.wsClient.useJaeeApi;

/**
 * Created by louis on 16-10-19.
 */
public interface MessageHandler {


    /**
     * process the message from web socket server
     * @param message
     */
    void handleMessage(String message);
}
