package io.github.oliverschen.gateway.inbound;

import io.github.oliverschen.gateway.filter.HttpRequestFilter;
import io.github.oliverschen.gateway.filter.PerRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

/**
 * @author ck
 */
public class PerRequestHandler extends ChannelInboundHandlerAdapter {
    private final HttpRequestFilter perRequestFilter;

    public PerRequestHandler() {
        perRequestFilter = new PerRequestFilter();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof HttpRequest) {
                FullHttpRequest fullRequest = (FullHttpRequest) msg;
                perRequestFilter.filter(fullRequest, ctx);
                ctx.fireChannelRead(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
