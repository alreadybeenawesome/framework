package com.okcoin.webscoketServer;

import com.centryOf22th.demo.netty.HttpFileServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by louis on 16-12-14.
 */
public class WebSocketServer {

    private String host;
    private int port;

    private Channel channel;
    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelPipeline channelPipeline;


    public WebSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void serverStart() {
        try {
            serverBootstrap = new ServerBootstrap();
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channelPipeline = channel.pipeline();
                            //消息解码器
                            channelPipeline.addLast("http-codec", new HttpServerCodec());
                            //将多个消息转换为单一的FullHttpRequest  聚合器
                            //原因是http解码器在每个http消息中会生成多个消息对象
                            //httpRequest httpResponse
                            //HttpContent   LastHttpContent
                            channelPipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
                            //ChunkedHandler 主要作用是支持异步发送打的码流，不占用过多的内存
                            channelPipeline.addLast("http-chuked", new ChunkedWriteHandler());
                            channelPipeline.addLast("handler", new WebSocketServerHandler());
                        }
                    });

            channel = serverBootstrap.bind(getPort()).sync().channel();

            System.out.println("websocket server started at port " + port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
