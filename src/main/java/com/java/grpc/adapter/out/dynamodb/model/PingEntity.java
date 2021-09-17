package com.java.grpc.adapter.out.dynamodb.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class PingEntity {

    public static final String TABLE_NAME = "PingEntity";
    private String address;
    private int time;

    public PingEntity() {
    }

    public PingEntity(String address, int time) {
        this.address = address;
        this.time = time;
    }

    @DynamoDbPartitionKey
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @DynamoDbSortKey
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
