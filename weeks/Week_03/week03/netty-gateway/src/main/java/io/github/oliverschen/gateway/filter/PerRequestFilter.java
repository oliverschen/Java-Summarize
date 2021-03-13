package io.github.oliverschen.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class PerRequestFilter implements HttpRequestFilter{

    private final static String HEADER_NIO = "nio";
    private final static String HEADER_VALUE = "ck";

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        System.out.println("add request header ==> nio,ck");
        fullRequest.headers().add(HEADER_NIO, HEADER_VALUE);
    }
}
