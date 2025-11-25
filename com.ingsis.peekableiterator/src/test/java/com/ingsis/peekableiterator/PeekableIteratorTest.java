/*
 * My Project
 */

package com.ingsis.peekableiterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeekableIteratorTest {

    private PeekableIterator<Integer> iterator;

    @BeforeEach
    void setUp() {
        // given: an iterator with three elements
        List<Integer> data = Arrays.asList(1, 2, 3);
        iterator = new SimplePeekableIterator<>(data.iterator());
    }

    @Test
    void givenIteratorWhenPeekThenDoesNotAdvance() {
        // when: we peek the first element
        Integer peeked = iterator.peek();

        // then: peek returns first element and does not advance the iterator
        assertEquals(1, peeked);
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
    }

    @Test
    void givenPeekThenNextReturnsSame() {
        // when
        Integer p = iterator.peek();
        Integer n = iterator.next();

        // then
        assertEquals(p, n);
    }

    @Test
    void givenPeekCalledMultipleTimesThenSameValue() {
        // when
        Integer p1 = iterator.peek();
        Integer p2 = iterator.peek();

        // then
        assertEquals(p1, p2);
        assertEquals(1, iterator.next());
    }

    @Test
    void givenEmptyIteratorWhenHasNextFalseAndPeekThrows() {
        // given: an empty iterator
        Iterator<Integer> empty = Arrays.<Integer>asList().iterator();
        PeekableIterator<Integer> it = new SimplePeekableIterator<>(empty);

        // then: hasNext is false and both peek and next throw
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::peek);
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void removeIsUnsupported() {
        // then: remove is unsupported
        assertThrows(UnsupportedOperationException.class, () -> iterator.remove());
    }

    // Simple test implementation used only in tests to verify expected semantics.
    private static final class SimplePeekableIterator<T> implements PeekableIterator<T> {
        private final Iterator<T> delegate;
        private T buffered;
        private boolean hasBuffered;

        SimplePeekableIterator(Iterator<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T peek() {
            if (!hasBuffered) {
                if (!delegate.hasNext()) {
                    throw new NoSuchElementException();
                }
                buffered = delegate.next();
                hasBuffered = true;
            }
            return buffered;
        }

        @Override
        public boolean hasNext() {
            return hasBuffered || delegate.hasNext();
        }

        @Override
        public T next() {
            if (hasBuffered) {
                T tmp = buffered;
                buffered = null;
                hasBuffered = false;
                return tmp;
            }
            return delegate.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
