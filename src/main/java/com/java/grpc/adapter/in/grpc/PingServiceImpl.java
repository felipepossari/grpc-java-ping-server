package com.java.grpc.adapter.in.grpc;

import com.java.grpc.PingRequest;
import com.java.grpc.PingResponse;
import com.java.grpc.PingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PingServiceImpl extends PingServiceGrpc.PingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(PingServiceImpl.class);

    @Override
    public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        log.info("Ping request received");
        PingResponse response = PingResponse.newBuilder()
                .setAddress(request.getAddress())
                .setTime(123)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
