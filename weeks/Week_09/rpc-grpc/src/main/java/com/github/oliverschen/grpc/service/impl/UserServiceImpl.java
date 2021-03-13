package com.github.oliverschen.grpc.service.impl;

import com.github.oliverschen.grpc.entity.MessageProto;
import com.github.oliverschen.grpc.rpc.UserRpcProto;
import com.github.oliverschen.grpc.rpc.UserRpcServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author ck
 */
public class UserServiceImpl extends UserRpcServiceGrpc.UserRpcServiceImplBase {

    @Override
    public void listByAge(UserRpcProto.AgeRequest request, StreamObserver<UserRpcProto.UserResponse> responseObserver) {
        // 构造响应，模拟业务逻辑
        UserRpcProto.UserResponse response = UserRpcProto.UserResponse.newBuilder()
                .setCode(0)
                .setMsg("success")
                .addUser(MessageProto.User.newBuilder()
                        .setName(RandomStringUtils.randomAlphabetic(5))
                        .setAge(request.getAge()).build())
                .addUser(MessageProto.User.newBuilder()
                        .setName(RandomStringUtils.randomAlphabetic(5))
                        .setAge(request.getAge()).build())
                .addUser(MessageProto.User.newBuilder()
                        .setName(RandomStringUtils.randomAlphabetic(5))
                        .setAge(request.getAge()).build())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
