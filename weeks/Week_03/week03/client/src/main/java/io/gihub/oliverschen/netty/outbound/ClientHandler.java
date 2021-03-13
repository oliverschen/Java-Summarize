package io.gihub.oliverschen.netty.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

import static io.netty.handler.codec.http.HttpMethod.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final String activeUrl;

    public ClientHandler(String activeUrl) {
        this.activeUrl = activeUrl;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI(activeUrl);
        FullHttpRequest request = new DefaultFullHttpRequest(HTTP_1_0, GET, uri.toASCIIString());
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("返回==>" + msg);
        if (msg instanceof HttpResponse) {
            DefaultHttpResponse response = (DefaultHttpResponse) msg;
            System.out.println("response==>" + response.toString());
        }
        if (msg instanceof HttpContent) {
            DefaultHttpContent content = (DefaultHttpContent) msg;
            ByteBuf buf = content.content();
            String s = buf.toString(CharsetUtil.UTF_8);
            System.out.println("解析==>" + s);
        }
        ctx.close();
    }

}
