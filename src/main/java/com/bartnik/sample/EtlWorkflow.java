package com.bartnik.sample;

import java.time.LocalDate;
import java.util.UUID;

import com.bartnik.ddd.AggregateRoot;
import com.bartnik.eventstore.EventLog;
import com.bartnik.eventstore.State;
import com.bartnik.sample.events.EtlInitiated;
import com.bartnik.sample.exception.InvalidOperationException;

import lombok.Getter;

@AggregateRoot
public class EtlWorkflow {

    @Getter
    private static class EtlWorkflowState implements State {

        private enum State {
            CREATED,
            INITIATED
        }

        private State state = State.CREATED;
        private LocalDate datasetDate;

        public void onEtlInitiated(final EtlInitiated event) {
            if (state == State.CREATED) {
                datasetDate = event.getDatasetDate();
            }
            else {
                throw new InvalidOperationException();
            }
        }
    }

    private final EtlWorkflowState state;
    private final EventLog<EtlWorkflowState> eventLog;

    public EtlWorkflow() {
        state = new EtlWorkflowState();
        eventLog = new EventLog<EtlWorkflow.EtlWorkflowState>(state);
    }

    public void initiate(final UUID executionId, final LocalDate datasetDate) {
        eventLog.push(new EtlInitiated(executionId, datasetDate));
    }
}
