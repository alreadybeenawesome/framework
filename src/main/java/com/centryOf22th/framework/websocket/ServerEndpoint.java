package com.centryOf22th.framework.websocket;

import com.sun.media.jfxmediaimpl.HostUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.*;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by louis on 16-12-12.
 */

@javax.websocket.server.ServerEndpoint("echoServer")
public class ServerEndpoint {


    private static final Logger logger = LogManager.getLogger(ServerEndpoint.class);


    private static final Vector<Session> sessions =new Vector<>();

    private Session session =null;


    private AtomicInteger count =new AtomicInteger(0);

    @OnOpen
    public void whenOpening(Session session, EndpointConfig endpointConfig){
        try {
            this.session=session;
            sessions.add(session);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void whenReceivingMessage(String message){
        count.incrementAndGet();
        if(message==null){
            sendMessage(this.session,"request parameters can not be null");
            return;
        }
        if(message.equals("ping")){
            sendMessage(this.session,"pong");
        }

    }


    @OnClose
    public void whenClosing(){

    }


    @OnError
    public void whenError(){

    }



    private void sendMessage(Session session,String message){
        if(session!=null&&session.isOpen()){
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
