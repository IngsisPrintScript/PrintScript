/*
 * My Project
 */

package com.ingsis.tokenstream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DefaultTokenStreamTest {

    static class SimplePeekable implements PeekableIterator<Token> {
        private final List<Token> list;
        private int idx = 0;

        SimplePeekable(List<Token> list) {
            this.list = new ArrayList<>(list);
        }

        @Override
        public Token peek() {
            return list.get(idx);
        }

        @Override
        public boolean hasNext() {
            return idx < list.size();
        }

        @Override
        public Token next() {
            if (idx >= list.size()) throw new java.util.NoSuchElementException();
            return list.get(idx++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    void consumeSkipsSpacesAndReturnsTokens() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token space = tf.createSpaceSeparatorToken("");
        Token a = tf.createLiteralToken("a");
        Token b = tf.createLiteralToken("b");

        List<Token> tokens = List.of(space, a, space, b);
        PeekableIterator<Token> it = new SimplePeekable(tokens);
        ResultFactory rf = new DefaultResultFactory();
        DefaultTokenStream stream = new DefaultTokenStream(it, rf);

        Result<Token> r1 = stream.consume();
        assertTrue(r1.isCorrect());
        assertEquals("a", r1.result().value());

        Result<Token> r2 = stream.consume();
        assertTrue(r2.isCorrect());
        assertEquals("b", r2.result().value());
    }

    @Test
    void consumeWithMismatchReturnsUnexpected() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token x = tf.createLiteralToken("x");
        Token expected = tf.createKeywordToken("if");
        PeekableIterator<Token> it = new SimplePeekable(List.of(x));
        DefaultTokenStream stream = new DefaultTokenStream(it, new DefaultResultFactory());

        Result<Token> res = stream.consume(expected);
        assertFalse(res.isCorrect());
        assertTrue(res.error().contains("Unexpected token"));
    }

    @Test
    void peekOffsetAndNextFollowBufferOrdering() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token a = tf.createLiteralToken("a");
        Token b = tf.createLiteralToken("b");
        Token c = tf.createLiteralToken("c");

        PeekableIterator<Token> it = new SimplePeekable(List.of(a, b, c));
        DefaultTokenStream stream = new DefaultTokenStream(it, new DefaultResultFactory());

        Token got = stream.peek(1);
        assertNotNull(got);
        assertEquals("b", got.value());

        // next should return the first element (buffered)
        Token n1 = stream.next();
        assertEquals("a", n1.value());

        Token n2 = stream.next();
        assertEquals("b", n2.value());

        Token n3 = stream.next();
        assertEquals("c", n3.value());
    }

    @Test
    void matchAndConsumeAllBehavior() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token sep = tf.createSeparatorToken(",");
        Token x = tf.createLiteralToken("x");
        // two separators then x
        PeekableIterator<Token> it = new SimplePeekable(List.of(sep, sep, x));
        DefaultTokenStream stream = new DefaultTokenStream(it, new DefaultResultFactory());

        // match should see first non-space token equal to sep
        assertTrue(stream.match(sep));

        // consumeAll counts separators
        Result<Integer> count = stream.consumeAll(sep);
        assertTrue(count.isCorrect());
        assertEquals(2, count.result().intValue());

        // now next should be x
        Token next = stream.next();
        assertEquals("x", next.value());
    }

    @Test
    void cleanBufferResetsPointerAndClears() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token a = tf.createLiteralToken("a");
        Token b = tf.createLiteralToken("b");
        Token c = tf.createLiteralToken("c");

        PeekableIterator<Token> it = new SimplePeekable(List.of(a, b, c));
        DefaultTokenStream stream = new DefaultTokenStream(it, new DefaultResultFactory());

        // populate buffer via peek
        Token p = stream.peek(2);
        assertEquals("c", p.value());

        // consume first two via next
        assertEquals("a", stream.next().value());
        assertEquals("b", stream.next().value());

        // now clean buffer: underlying iterator has been advanced
        stream.cleanBuffer();
        assertFalse(stream.hasNext());
        assertThrows(java.util.NoSuchElementException.class, () -> stream.next());
    }
}
