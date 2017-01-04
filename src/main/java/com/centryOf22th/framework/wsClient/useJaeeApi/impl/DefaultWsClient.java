package com.centryOf22th.framework.wsClient.useJaeeApi.impl;

import com.centryOf22th.framework.wsClient.useJaeeApi.MessageHandler;
import com.centryOf22th.framework.wsClient.useJaeeApi.WebSocketClientEndpoint;
import com.centryOf22th.framework.wsClient.useJaeeApi.WsClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by louis on 16-10-19.
 */

@Service
public class DefaultWsClient implements WsClient {

    @Override
    public void start() throws Exception {
        final String hostAndPort ="ws://172.16.5.154:8080/transport";
        final String hostAndPort2 ="wss://mobile.lhang.com/ws/transport";
        final String hostAndPort3 ="ws://172.16.1.69:8080/exchange-mobile/transport";

        // open websocket
        final WebSocketClientEndpoint webSocketClientEndpoint = new WebSocketClientEndpoint(new URI(hostAndPort2));

        // add listener
        webSocketClientEndpoint.addMessageHandler(new MessageHandler() {
            public void handleMessage(String message) {
                System.out.println("message from server:" + message);
            }
        });

        String sendMsg = "{\"channel\":\"sub_spot_btc_cny_dailydata\"}";
//        String sendMsg = "{\"channel\":\"\"sub_spot_btc_cny_kline_1minute\"}";

        webSocketClientEndpoint.sendMessage(sendMsg);


    }
}
