package com.ingsis.utils.process.checkpoint;

import com.ingsis.utils.iterator.safe.SafeIterator;

public final class ProcessCheckpoint<S, M, R> {
  private final R result;
  private final SafeIterator<S> iterator;
  private final M medium;
  private static final ProcessCheckpoint<?, ?, ?> UNINITIALIZED_SINGLETON = new ProcessCheckpoint<>(null, null, null);

  private ProcessCheckpoint(R result, SafeIterator<S> iterator, M medium) {
    this.result = result;
    this.iterator = iterator;
    this.medium = medium;
  }

  @SuppressWarnings("unchecked")
  public static <S, M, R> ProcessCheckpoint<S, M, R> UNINITIALIZED() {
    // Cast singleton to the correct generic type
    return (ProcessCheckpoint<S, M, R>) UNINITIALIZED_SINGLETON;
  }

  public static <S, M, R> ProcessCheckpoint<S, M, R> INITIALIZED(SafeIterator<S> iterator, M medium, R result) {
    return new ProcessCheckpoint<>(result, iterator, medium);
  }

  public R result() {
    return this.result;
  }

  public SafeIterator<S> iterator() {
    return this.iterator;
  }

  public M medium() {
    return this.medium;
  }

  public boolean isUninitialized() {
    return this == UNINITIALIZED_SINGLETON;
  }
}
