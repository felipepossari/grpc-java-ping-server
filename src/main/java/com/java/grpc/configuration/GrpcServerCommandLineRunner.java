package com.java.grpc.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcServerCommandLineRunner implements CommandLineRunner {

    private final GrpcServerConfiguration grpcServerConfiguration;

    public GrpcServerCommandLineRunner(GrpcServerConfiguration grpcServerConfiguration) {
        this.grpcServerConfiguration = grpcServerConfiguration;
    }

    @Override
    public void run(String... args) throws Exception {
        grpcServerConfiguration.start();
        grpcServerConfiguration.block();
    }
}
