package com.centryOf22th.framework.wsClient.useJaeeApi;

import com.centryOf22th.framework.wsClient.useJaeeApi.impl.DefaultWsClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by louis on 16-10-19.
 */
public class Run {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        WsClient wsClient =new DefaultWsClient();
        wsClient.start();

        latch.await();


    }
}
