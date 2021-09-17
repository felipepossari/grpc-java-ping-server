package com.java.grpc.configuration;

import com.java.grpc.adapter.out.dynamodb.model.PingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

import java.net.URI;

@Configuration
public class DynamoDbConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDbConfiguration.class);

    @Value("${aws.endpoint}")
    private String awsEndpoint;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.SA_EAST_1)
                .endpointOverride(URI.create(awsEndpoint))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbTable<PingEntity> pingDbTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        var pingEntityTable = dynamoDbEnhancedClient.table(PingEntity.TABLE_NAME,
                TableSchema.fromBean(PingEntity.class));
        createTable(pingEntityTable);
        return pingEntityTable;
    }

    private void createTable(DynamoDbTable<?> table) {
        try {
            LOG.info("Creating table {}.", table.tableName());
            table.createTable();
        } catch (ResourceInUseException ex) {
            LOG.info("Table {} alread created.", table.tableName());
        }
    }
}
