package com.example.megaservice.service;

import com.example.megaservice.grpc.hello.HelloServiceGrpc;
import com.example.megaservice.grpc.hello.HelloServiceOuterClass.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class BasicGrpcService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request,
                      StreamObserver<HelloReply> responseObserver) {
        HelloReply helloReply = HelloReply.newBuilder()
                .setMessage("Hello, " + request.getName() + '!')
                .build();

        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
    }
}
