package common;

import java.util.Iterator;

public interface PeekableIterator<T> extends Iterator<T> {
    T peek();
}
