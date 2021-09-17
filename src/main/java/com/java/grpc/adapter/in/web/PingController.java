package com.java.grpc.adapter.in.web;

import com.java.grpc.PingRequest;
import com.java.grpc.PingResponse;
import com.java.grpc.adapter.in.web.request.PingWebRequest;
import com.java.grpc.adapter.in.web.response.PingWebResponse;
import com.java.grpc.configuration.GrpcClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

    private final GrpcClientConfiguration grpcClientConfiguration;

    public PingController(GrpcClientConfiguration grpcClientConfiguration) {
        this.grpcClientConfiguration = grpcClientConfiguration;
    }

    @PostMapping("/ping-unary")
    public ResponseEntity<PingWebResponse> pingUnary(@RequestBody PingWebRequest request) {
        LOG.info("Pinging address {}", request.getAddress());
        PingResponse grpcResponse = grpcClientConfiguration.getStub().ping(
                buildRequest(request));

        var response = new PingWebResponse(grpcResponse.getAddress(), grpcResponse.getTime());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ping-server-stream")
    public ResponseEntity<Void> pingServerCall(@RequestBody PingWebRequest request) {
        LOG.info("Pinging address {}", request.getAddress());

        grpcClientConfiguration.getStub().pingServerStream(buildRequest(request))
                .forEachRemaining(response ->
                        LOG.info("Ping response: {} - {} ", response.getAddress(), response.getTime())
                );
        return ResponseEntity.ok().build();
    }

    private PingRequest buildRequest(PingWebRequest request) {
        return PingRequest.newBuilder()
                .setAddress(request.getAddress())
                .build();
    }
}
