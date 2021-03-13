package io.gihub.oliverschen.netty;

import io.gihub.oliverschen.netty.outbound.ClientInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public final static int PORT = 8888;
    public final static String URL = "localhost";

    public static void main(String[] args) {
        connect("/hello");
    }

    public static void connect(final String activeUrl) {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInit(activeUrl));

        try {
            ChannelFuture future = bootstrap.connect(URL, PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException  e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }finally {
            workGroup.shutdownGracefully();
        }
    }


}
