/*
 * My Project
 */

package com.ingsis.peekableiterator;

import java.util.Iterator;

public interface PeekableIterator<T> extends Iterator<T> {
    T peek();
}
