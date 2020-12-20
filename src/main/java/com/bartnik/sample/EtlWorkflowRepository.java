package com.bartnik.sample;

import java.util.UUID;

public class EtlWorkflowRepository {
  public EtlWorkflow create() {
    return new EtlWorkflow();
  }

  public EtlWorkflow load(final UUID executionId) {
    final EtlWorkflow workflow = new EtlWorkflow();

    // create a new EtlWorkflowState
    // use the Store to load all events for this workflow id
    // replay them using EventLog and the state created in line 1

    return workflow;
  }
}
