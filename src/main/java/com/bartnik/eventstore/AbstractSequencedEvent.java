package com.bartnik.eventstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Basic API for any sequenced event.
 *
 * Sequenced events describe incremental changes to the state of an aggregate,
 * identified by {@code source}. They need to be ordered, hence the
 * {@code sequenceNumber}.
 *
 * Events should be treated as immutable: the setters are to be used only for
 * deserialization purposes.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSequencedEvent implements SequencedEvent {
    protected UUID source;
    protected long sequenceNumber;
}
