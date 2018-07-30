package com.letv.mas.caller.demo.service;

import net.devh.grpc.demo.HelloRequest;
import net.devh.grpc.demo.HelloResponse;
import net.devh.grpc.demo.HelloServiceGrpc;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * Created by leeco on 18/5/12.
 */
@Service
public class GrpcClientService {

    @GrpcClient("letv-mas-client")
    private Channel serverChannel;

    public String sendMessage(String name) {
        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(serverChannel);
        HelloResponse response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}