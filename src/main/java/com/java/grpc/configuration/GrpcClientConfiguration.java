package com.java.grpc.configuration;

import com.java.grpc.PingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GrpcClientConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcClientConfiguration.class);
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 50051;

    private ManagedChannel managedChannel;
    private PingServiceGrpc.PingServiceBlockingStub stub;

    public void start() {
        managedChannel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        stub = PingServiceGrpc.newBlockingStub(managedChannel);
        LOG.info("gRPC client started. Address: {}:{}", HOST, PORT);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        LOG.info("gRPC client shut down successfully");
    }

    public PingServiceGrpc.PingServiceBlockingStub getStub() {
        return this.stub;
    }
}
