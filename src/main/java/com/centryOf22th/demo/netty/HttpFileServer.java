package com.centryOf22th.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by louis on 16-12-8.
 */
public class HttpFileServer {
    private static final String  DEFAULT_URL="/workspace/exchange-mobile/";

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //消息解码器
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            //将多个消息转换为单一的FullHttpRequest  聚合器
                            //原因是http解码器在每个http消息中会生成多个消息对象
                            //httpRequest httpResponse
                            //HttpContent   LastHttpContent
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            //ChunkedHandler 主要作用是支持异步发送打的码流，不占用过多的内存
                            ch.pipeline().addLast("http-chuked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));

                        }
                    });
            ChannelFuture future =b.bind("localhost",port).sync();
            System.out.println("HTTP文件目录服务器启动 ，网址是 "+"http://172.16.3.58:"+port+url);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception{
        int port =8080;
        if(args.length>0){
            try {
                port=Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url=DEFAULT_URL;
        if(args.length>1){
            url=args[1];
            new HttpFileServer().run(port,url);
        }
    }
}
