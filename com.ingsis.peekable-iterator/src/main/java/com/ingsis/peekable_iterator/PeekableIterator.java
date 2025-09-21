package com.ingsis.peekable_iterator;

import java.util.Iterator;

public interface PeekableIterator<T> extends Iterator<T> {
    T peek();
}
