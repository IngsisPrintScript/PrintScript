/*
 * My Project
 */

package com.ingsis.utils.process.checkpoint;

import com.ingsis.utils.iterator.safe.SafeIterator;

public final class ProcessCheckpoint<S, R> {
    private final R result;
    private final SafeIterator<S> iterator;
    private static final ProcessCheckpoint<?, ?> UNINITIALIZED_SINGLETON =
            new ProcessCheckpoint<>(null, null);

    private ProcessCheckpoint(SafeIterator<S> iterator, R result) {
        this.iterator = iterator;
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public static <S, R> ProcessCheckpoint<S, R> UNINITIALIZED() {
        return (ProcessCheckpoint<S, R>) UNINITIALIZED_SINGLETON;
    }

    public static <S, R> ProcessCheckpoint<S, R> INITIALIZED(SafeIterator<S> iterator, R result) {
        return new ProcessCheckpoint<>(iterator, result);
    }

    public R result() {
        return this.result;
    }

    public SafeIterator<S> iterator() {
        return this.iterator;
    }

    public boolean isUninitialized() {
        return this == UNINITIALIZED_SINGLETON;
    }

    public boolean isInitialized() {
        return !isUninitialized();
    }
}
