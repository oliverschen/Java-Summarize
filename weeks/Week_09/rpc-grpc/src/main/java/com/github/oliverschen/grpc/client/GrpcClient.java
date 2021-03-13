package com.github.oliverschen.grpc.client;

import com.github.oliverschen.grpc.rpc.UserRpcProto;
import com.github.oliverschen.grpc.rpc.UserRpcServiceGrpc;
import com.googlecode.protobuf.format.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

import static com.github.oliverschen.grpc.constant.Consts.IP;
import static com.github.oliverschen.grpc.constant.Consts.SERVER_PORT;

/**
 * @author ck
 */
public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(IP, SERVER_PORT).usePlaintext().build();
        UserRpcServiceGrpc.UserRpcServiceBlockingStub stub = UserRpcServiceGrpc.newBlockingStub(channel);
        UserRpcProto.AgeRequest request = UserRpcProto.AgeRequest.newBuilder().setAge(20).build();
        try {
            UserRpcProto.UserResponse response = stub.listByAge(request);
            System.out.println(new JsonFormat().printToString(response));
        }finally {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }

    }

}
