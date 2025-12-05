package com.ingsis.utils.iterator.safe.result;

import com.ingsis.utils.iterator.safe.SafeIterator;

public class InMemoryIterationResultFactory implements IterationResultFactory {

  @Override
  public <T> SafeIterationResult<T> createCorrectResult(T iterationResult, SafeIterator<T> nextIterator) {
    return CorrectIterationResult.build(iterationResult, nextIterator);
  }

  @Override
  public <T> SafeIterationResult<T> createIncorrectResult(String error) {
    return IncorrectIterationResult.build(error);
  }

  @Override
  public <T> SafeIterationResult<T> cloneIncorrectResult(SafeIterationResult<?> originalIncorrectResult) {
    return IncorrectIterationResult.build(originalIncorrectResult.error());
  }

}
