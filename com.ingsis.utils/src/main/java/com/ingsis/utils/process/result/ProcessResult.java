package com.ingsis.utils.process.result;

import com.ingsis.utils.process.state.ProcessState;

public final class ProcessResult<R> {

  private final R result;
  private final ProcessState status;
  private final Integer priority;

  private ProcessResult(R result, ProcessState status, Integer priority) {
    this.result = result;
    this.status = status;
    this.priority = priority;
  }

  public static <R> ProcessResult<R> COMPLETE(R result, int priority) {
    return new ProcessResult<>(result, ProcessState.COMPLETE, priority);
  }

  public static <R> ProcessResult<R> PREFIX(int priority) {
    return new ProcessResult<>(null, ProcessState.PREFIX, priority);
  }

  public static <R> ProcessResult<R> INVALID() {
    return new ProcessResult<>(null, ProcessState.INVALID, null);
  }

  public R result() {
    return this.result;
  }

  public ProcessState status() {
    return this.status;
  }

  public Integer priority() {
    return this.priority;
  }

  public ProcessResult<R> comparePriority(ProcessResult<R> other) {

    if (this.isComplete() && !other.isComplete())
      return this;
    if (!this.isComplete() && other.isComplete())
      return other;

    if (!this.isComplete() && !other.isComplete()) {
      return this;
    }

    return this.priority < other.priority ? this : other;
  }

  public boolean isComplete() {
    return this.status == ProcessState.COMPLETE;
  }

  public boolean isPrefix() {
    return this.status == ProcessState.PREFIX;
  }

  public boolean isInvalid() {
    return this.status == ProcessState.INVALID;
  }
}
