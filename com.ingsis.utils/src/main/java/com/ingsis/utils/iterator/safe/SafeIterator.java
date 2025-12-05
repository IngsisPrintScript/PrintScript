package com.ingsis.utils.iterator.safe;

import com.ingsis.utils.iterator.safe.result.SafeIterationResult;

public interface SafeIterator<T> {
  SafeIterationResult<T> next();
}
