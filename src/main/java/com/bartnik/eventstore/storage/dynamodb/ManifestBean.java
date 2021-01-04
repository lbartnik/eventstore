package com.bartnik.eventstore.storage.dynamodb;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@Builder
public class ManifestBean {

  public static final String VERSION_ATTRIBUTE_NAME = "version";

  private String id;
  private long version;

  @DynamoDbPartitionKey
  public String getId() {
    return this.id;
  }
}