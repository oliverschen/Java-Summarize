package com.github.oliverschen.grpc.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: grpc_user.proto")
public final class UserRpcServiceGrpc {

  private UserRpcServiceGrpc() {}

  public static final String SERVICE_NAME = "UserRpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<UserRpcProto.AgeRequest,
      UserRpcProto.UserResponse> getListByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listByAge",
      requestType = UserRpcProto.AgeRequest.class,
      responseType = UserRpcProto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<UserRpcProto.AgeRequest,
      UserRpcProto.UserResponse> getListByAgeMethod() {
    io.grpc.MethodDescriptor<UserRpcProto.AgeRequest, UserRpcProto.UserResponse> getListByAgeMethod;
    if ((getListByAgeMethod = UserRpcServiceGrpc.getListByAgeMethod) == null) {
      synchronized (UserRpcServiceGrpc.class) {
        if ((getListByAgeMethod = UserRpcServiceGrpc.getListByAgeMethod) == null) {
          UserRpcServiceGrpc.getListByAgeMethod = getListByAgeMethod =
              io.grpc.MethodDescriptor.<UserRpcProto.AgeRequest, UserRpcProto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  UserRpcProto.AgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  UserRpcProto.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserRpcServiceMethodDescriptorSupplier("listByAge"))
              .build();
        }
      }
    }
    return getListByAgeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserRpcServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserRpcServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserRpcServiceStub>() {
        @Override
        public UserRpcServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserRpcServiceStub(channel, callOptions);
        }
      };
    return UserRpcServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserRpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserRpcServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserRpcServiceBlockingStub>() {
        @Override
        public UserRpcServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserRpcServiceBlockingStub(channel, callOptions);
        }
      };
    return UserRpcServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserRpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserRpcServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserRpcServiceFutureStub>() {
        @Override
        public UserRpcServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserRpcServiceFutureStub(channel, callOptions);
        }
      };
    return UserRpcServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UserRpcServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void listByAge(UserRpcProto.AgeRequest request,
                          io.grpc.stub.StreamObserver<UserRpcProto.UserResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListByAgeMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListByAgeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                UserRpcProto.AgeRequest,
                UserRpcProto.UserResponse>(
                  this, METHODID_LIST_BY_AGE)))
          .build();
    }
  }

  /**
   */
  public static final class UserRpcServiceStub extends io.grpc.stub.AbstractAsyncStub<UserRpcServiceStub> {
    private UserRpcServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected UserRpcServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserRpcServiceStub(channel, callOptions);
    }

    /**
     */
    public void listByAge(UserRpcProto.AgeRequest request,
                          io.grpc.stub.StreamObserver<UserRpcProto.UserResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListByAgeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserRpcServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UserRpcServiceBlockingStub> {
    private UserRpcServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected UserRpcServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserRpcServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public UserRpcProto.UserResponse listByAge(UserRpcProto.AgeRequest request) {
      return blockingUnaryCall(
          getChannel(), getListByAgeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserRpcServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UserRpcServiceFutureStub> {
    private UserRpcServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected UserRpcServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserRpcServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<UserRpcProto.UserResponse> listByAge(
        UserRpcProto.AgeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListByAgeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_BY_AGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserRpcServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserRpcServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_BY_AGE:
          serviceImpl.listByAge((UserRpcProto.AgeRequest) request,
              (io.grpc.stub.StreamObserver<UserRpcProto.UserResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UserRpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserRpcServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return UserRpcProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserRpcService");
    }
  }

  private static final class UserRpcServiceFileDescriptorSupplier
      extends UserRpcServiceBaseDescriptorSupplier {
    UserRpcServiceFileDescriptorSupplier() {}
  }

  private static final class UserRpcServiceMethodDescriptorSupplier
      extends UserRpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserRpcServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UserRpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserRpcServiceFileDescriptorSupplier())
              .addMethod(getListByAgeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
