package com.centryOf22th.framework.wsClient.demo;

/**
 * Created by louis on 16-10-18.
 */



public class Test {

//    public static void main(String[] args) throws Exception {
//        WebSocketClient mWs = new WebSocketClient(new URI("ws://139.196.86.139/ws/transport"), new Draft_10()) {
//            @Override
//            public void onMessage(String message) {
//                System.out.println(message);
//                JSONObject obj = new JSONObject(message);
//                String channel = obj.getString("channel");
//            }
//
//            @Override
//            public void onOpen(ServerHandshake handshake) {
//                System.out.println("opened connection");
//            }
//
//            @Override
//            public void onClose(int code, String reason, boolean remote) {
//                System.out.println("closed connection");
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                ex.printStackTrace();
//            }
//
//        };
//        //open websocket
//        mWs.connect();
//        JSONObject obj = new JSONObject();
//        String jsonString = "{\"channel\":\"sub_spot_btc_cny_kline_1minute\"}";
//        obj.put("event", "addChannel");
//        obj.put("channel", "ok_btccny_ticker");
//        String message = obj.toString();
//        //send message
//        mWs.send(jsonString);
//    }


}
