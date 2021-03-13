package com.github.oliverschen.grpc.server;

import com.github.oliverschen.grpc.service.impl.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.github.oliverschen.grpc.constant.Consts.SERVER_PORT;

/**
 *
 * @author ck
 */
public class GrpcServer {

    private Server server;
    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }

    /**
     * 等待钩子线程关闭 server
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void start() throws IOException {
        server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new UserServiceImpl())
                .build()
                .start();

         // 监听服务关闭钩子线程
         Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.err.println("server is stopping...");
            try {
                GrpcServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("server error");
            }
            System.err.println("server was stopped");
        }));

    }

    /**
     * 关闭服务
     */
    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }
    }
}
