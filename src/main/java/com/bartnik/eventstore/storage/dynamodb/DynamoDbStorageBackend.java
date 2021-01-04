package com.bartnik.eventstore.storage.dynamodb;

import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.EventSourcedAggregate;
import com.bartnik.eventstore.exception.EventStoreError;
import com.bartnik.eventstore.storage.StorageBackend;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DynamoDbStorageBackend implements StorageBackend {

    @NonNull private final DynamoDbEnhancedClient dynamoDbClient;
    @NonNull private final String manifestTableName;
    @NonNull private final String eventsTableName;

    private Optional<DynamoDbTable<ManifestBean>> manifestTable = Optional.empty();
    private Optional<DynamoDbTable<EventBean>> eventsTable = Optional.empty();

    @Override
    public void save(@NonNull final EventSourcedAggregate aggregate) {

        final ManifestBean newValue = ManifestBean.builder()
            .id(aggregate.getId().toString())
            .version(aggregate.getVersion())
            .build();

        final Expression optimisticLockingCondition = Expression.builder()
            .expression("#attribute = :value")
            .putExpressionName("#attribute", ManifestBean.VERSION_ATTRIBUTE_NAME)
            .putExpressionValue(":value", AttributeValue.builder()
                .n(aggregate.getEventManager().getReferenceVersion())
                .build())
            .build();

        final PutItemEnhancedRequest<ManifestBean> request =
            PutItemEnhancedRequest.builder(ManifestBean.class)
            .item(newValue)
            .conditionExpression(optimisticLockingCondition)
            .build();

        // TODO exception handling - detect optimistic locking issue
        getManifestTable().putItem(request);

        // TODO if optimistic locking passes, write events
    }

    @Override
    public EventCollection load(UUID id) throws EventStoreError {
        return null;
    }

    protected DynamoDbTable<ManifestBean> getManifestTable() {
        if (!manifestTable.isPresent()) {
            manifestTable = Optional.of(dynamoDbClient.table(manifestTableName, TableSchema.fromBean(ManifestBean.class)));
        }

        return manifestTable.get();
    }

    protected DynamoDbTable<EventBean> getEventsTable() {
        return eventsTable.get();
    }

}
