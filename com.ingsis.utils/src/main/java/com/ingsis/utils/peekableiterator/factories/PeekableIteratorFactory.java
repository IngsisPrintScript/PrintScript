/*
 * My Project
 */

package com.ingsis.utils.peekableiterator.factories;

import com.ingsis.utils.peekableiterator.PeekableIterator;
import java.io.InputStream;

public interface PeekableIteratorFactory<T> {

    PeekableIterator<T> fromInputStream(InputStream in);
}
