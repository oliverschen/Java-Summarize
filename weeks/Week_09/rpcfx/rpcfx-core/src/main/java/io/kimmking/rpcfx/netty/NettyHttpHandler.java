package io.kimmking.rpcfx.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;

/**
 * @author ck
 */
@Slf4j
public class NettyHttpHandler extends ChannelInboundHandlerAdapter {


    private final String url;
    public NettyHttpHandler(String url) {
        this.url = url;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 构建HTTP请求
        FullHttpRequest request =
                new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url);
        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
                .add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        log.info("netty client request url:{}", url);
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("netty client channel read msg: {}", msg);
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = null;
            try {
                response = (FullHttpResponse) msg;
                ByteBuf buf = response.content();
                String result = buf.toString(CharsetUtil.UTF_8);
                response.headers().set(CONTENT_TYPE, APPLICATION_JSON);
                log.info("msg:{}", result);
            } finally {
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
