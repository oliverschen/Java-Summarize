package io.gihub.oliverschen.netty.outbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class ClientInit extends ChannelInitializer<SocketChannel> {

    private String activeUrl;

    public ClientInit(String activeUrl) {
        this.activeUrl = activeUrl;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new HttpRequestEncoder())
                .addLast(new HttpResponseDecoder())
                .addLast(new ClientHandler(activeUrl));
    }
}
