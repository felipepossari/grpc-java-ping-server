package com.java.grpc.configuration;

import com.java.grpc.PingServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(GrpcServerConfiguration.class);
    private static final int SERVER_PORT = 50051;
    private Server server;

    private final PingServiceGrpc.PingServiceImplBase pingServiceImplBase;

    public GrpcServerConfiguration(PingServiceGrpc.PingServiceImplBase pingServiceImplBase) {
        this.pingServiceImplBase = pingServiceImplBase;
    }

    public void start() throws IOException {
        log.info("Starting gRPC server on port {}", SERVER_PORT);
        server = ServerBuilder.forPort(SERVER_PORT)
                .addService(pingServiceImplBase)
                .build()
                .start();
        log.info("gRPC server started.");

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    log.info("Shutting down gRPC server.");
                    GrpcServerConfiguration.this.stop();
                    log.info("gRPC shut down");
                })
        );
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void block() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
