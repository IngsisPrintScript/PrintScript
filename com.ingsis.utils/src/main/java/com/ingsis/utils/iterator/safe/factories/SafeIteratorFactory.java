/*
 * My Project
 */

package com.ingsis.utils.iterator.safe.factories;

import java.io.InputStream;

import com.ingsis.utils.iterator.safe.SafeIterator;

public interface SafeIteratorFactory<T> {
  SafeIterator<T> fromInputStream(InputStream in);
}
