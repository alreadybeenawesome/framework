package com.centryOf22th.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import static io.netty.handler.ssl.ApplicationProtocolNames.HTTP_1_1;

/**
 * Created by louis on 16-12-8.
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (!msg.getDecoderResult().isSuccess()) {

        }
    }



    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ByteBuf byteBuf =Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8);
//        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, byteBuf);
//        response.headers().set("content_type","text/plain; charset=UTF-8");
//        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
