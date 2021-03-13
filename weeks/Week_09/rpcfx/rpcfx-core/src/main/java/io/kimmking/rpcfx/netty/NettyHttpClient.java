package io.kimmking.rpcfx.netty;

import io.kimmking.rpcfx.exception.RpcfxException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @author ck
 */
public class NettyHttpClient {

    public void execute(String url,String host,Integer port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new HttpClientCodec());
                            p.addLast(new HttpObjectAggregator(1024 * 1024));
                            p.addLast(new HttpContentDecompressor());
                            p.addLast(new NettyHttpHandler(url));
                        }
                    });
            // 连接
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            // 等待关闭连接
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RpcfxException("netty client error", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
