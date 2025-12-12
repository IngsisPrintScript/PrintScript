/*
 * My Project
 */

package com.ingsis.utils.iterator.safe.factories;

import com.ingsis.utils.iterator.safe.SafeIterator;
import java.io.InputStream;

public interface SafeIteratorFactory<T> {
  SafeIterator<T> fromInputStream(InputStream in);

  SafeIterator<T> fromInputStreamLogger(InputStream in);
}
