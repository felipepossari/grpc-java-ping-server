package com.java.grpc.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcClientCommandLineRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcClientCommandLineRunner.class);
    private final GrpcClientConfiguration grpcClientConfiguration;

    public GrpcClientCommandLineRunner(GrpcClientConfiguration grpcClientConfiguration) {
        this.grpcClientConfiguration = grpcClientConfiguration;
    }

    @Override
    public void run(String... args) throws Exception {
        grpcClientConfiguration.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                grpcClientConfiguration.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
