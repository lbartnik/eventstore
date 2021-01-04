package com.bartnik.eventstore.storage.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Value;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Value
@AllArgsConstructor
public class EventBean {

  private String source;
  private long sequenceNumber;
  private String payload;

  @DynamoDbPartitionKey
  public String getSource() {
    return source;
  }

  @DynamoDbSortKey
  public long getSequenceNumber() {
    return sequenceNumber;
  }
}
