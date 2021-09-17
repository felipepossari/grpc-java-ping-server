package com.java.grpc.adapter.out.dynamodb;

import com.java.grpc.adapter.out.dynamodb.model.PingEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Component
public class PingRepositoryImpl implements PingRepository {

    private final DynamoDbTable<PingEntity> pingDbTable;

    public PingRepositoryImpl(DynamoDbTable<PingEntity> pingDbTable) {
        this.pingDbTable = pingDbTable;
    }

    @Override
    public void save(PingEntity pingEntity) {
        pingDbTable.putItem(pingEntity);
    }
}
