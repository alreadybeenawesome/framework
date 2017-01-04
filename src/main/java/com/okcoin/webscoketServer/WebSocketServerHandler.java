package com.okcoin.webscoketServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.Objects;

/**
 * Created by louis on 16-12-14.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){

        }
        else if(msg instanceof WebSocketFrame){

        }
     }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest request) throws Exception{
        if(request.getDecoderResult().isSuccess()||"websocket".equals(request.headers().get("Upgrade"))){

        }else{

        }

    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) throws Exception{
        if(response.getStatus().code()!=200){
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
         }
         //如果是非keep-alive 关闭链接
        ChannelFuture f =ctx.channel().writeAndFlush(response);
    }
}
