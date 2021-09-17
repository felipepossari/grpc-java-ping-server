package com.java.grpc.adapter.in.grpc;

import com.java.grpc.PingRequest;
import com.java.grpc.PingResponse;
import com.java.grpc.PingServiceGrpc;
import com.java.grpc.adapter.out.dynamodb.PingRepository;
import com.java.grpc.adapter.out.dynamodb.model.PingEntity;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PingServiceImpl extends PingServiceGrpc.PingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(PingServiceImpl.class);
    private Random random = new Random(123456L);

    private final PingRepository pingRepository;

    public PingServiceImpl(PingRepository pingRepository) {
        this.pingRepository = pingRepository;
    }

    @Override
    public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        log.info("Unary - Ping request received");
        var response = buildPingResponse(request);
        responseObserver.onNext(response);

        savePingResponse(response);

        responseObserver.onCompleted();
    }

    @Override
    public void pingServerStream(PingRequest request, StreamObserver<PingResponse> responseObserver) {
        try {
            log.info("ServerStream - Ping request received");
            for (var i = 0; i < 10; i++) {
                var response = buildPingResponse(request);
                responseObserver.onNext(response);
                savePingResponse(response);
                Thread.sleep(500L);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }

    private PingResponse buildPingResponse(PingRequest request) {
        return PingResponse.newBuilder()
                .setAddress(request.getAddress())
                .setTime(random.nextInt(1000))
                .build();
    }

    private void savePingResponse(PingResponse response) {
        pingRepository.save(new PingEntity(response.getAddress(), response.getTime()));
    }
}
