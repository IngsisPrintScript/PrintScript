package com.ingsis.utils.iterator.safe.result;

import com.ingsis.utils.iterator.safe.SafeIterator;

public interface IterationResultFactory {
  <T> SafeIterationResult<T> createCorrectResult(T iterationResult,
      SafeIterator<T> nextIterator);

  <T> SafeIterationResult<T> createIncorrectResult(String error);

  <T> SafeIterationResult<T> cloneIncorrectResult(SafeIterationResult<?> originalIncorrectResult);
}
