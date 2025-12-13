/*
 * My Project
 */

package com.ingsis.utils.process.checkpoint;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.process.state.ProcessState;

public final class ProcessCheckpoint<S, R> {
    private final R result;
    private final SafeIterator<S> iterator;
    private final ProcessState status;
    private final Integer priority;

    private static final ProcessCheckpoint<?, ?> UNINITIALIZED_SINGLETON =
            new ProcessCheckpoint<>(null, null, ProcessState.INVALID, null);

    private ProcessCheckpoint(
            SafeIterator<S> iterator, R result, ProcessState status, Integer priority) {
        this.iterator = iterator;
        this.result = result;
        this.status = status;
        this.priority = priority;
    }

    @SuppressWarnings("unchecked")
    public static <S, R> ProcessCheckpoint<S, R> UNINITIALIZED() {
        return (ProcessCheckpoint<S, R>) UNINITIALIZED_SINGLETON;
    }

    public static <S, R> ProcessCheckpoint<S, R> INITIALIZED(
            SafeIterator<S> iterator, R result, Integer priority) {
        return new ProcessCheckpoint<>(iterator, result, ProcessState.COMPLETE, priority);
    }

    public R result() {
        return this.result;
    }

    public Integer priority() {
        return this.priority;
    }

    public ProcessState status() {
        return this.status;
    }

    public SafeIterator<S> iterator() {
        return this.iterator;
    }

    public boolean isUninitialized() {
        return !this.status.isValid();
    }

    public Boolean isInitialized() {
        return !isUninitialized();
    }
}
