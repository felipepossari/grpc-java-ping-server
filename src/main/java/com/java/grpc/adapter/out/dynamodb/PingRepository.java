package com.java.grpc.adapter.out.dynamodb;

import com.java.grpc.adapter.out.dynamodb.model.PingEntity;

public interface PingRepository {

    public void save(PingEntity pingEntity);
}
