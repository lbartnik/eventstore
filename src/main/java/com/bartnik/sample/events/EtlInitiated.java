package com.bartnik.sample.events;

import java.time.LocalDate;
import java.util.UUID;

import com.bartnik.eventstore.Event;

import lombok.Value;

@Value
public class EtlInitiated implements Event {
    UUID executionId;
    LocalDate datasetDate;
}
