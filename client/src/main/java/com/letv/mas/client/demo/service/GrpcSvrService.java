package com.letv.mas.client.demo.service;

import net.devh.grpc.demo.HelloServiceGrpc;
import net.devh.grpc.demo.HelloRequest;
import net.devh.grpc.demo.HelloResponse;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by leeco on 18/5/12.
 */
@ConditionalOnProperty(value = "grpc.server.port", matchIfMissing = false)
@GrpcService(HelloServiceGrpc.class)
public class GrpcSvrService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder().setMessage("hi " + req.getName() + ",i am from rpc").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}