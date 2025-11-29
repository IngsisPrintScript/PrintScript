/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TypeParserTest {

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

    // Minimal TokenStream implementation for tests that returns pre-defined tokens.
    static final class TokenStreamSimple implements TokenStream {
        private final List<Token> tokens;
        private int idx = 0;

        TokenStreamSimple(List<Token> tokens) {
            this.tokens = new ArrayList<>(tokens);
        }

        @Override
        public boolean match(Token tokenTemplate) {
            if (!hasNext()) return false;
            return tokens.get(idx).equals(tokenTemplate);
        }

        @Override
        public Result<Token> consume() {
            if (!hasNext()) return new IncorrectResult<>("No more tokens");
            return new CorrectResult<>(tokens.get(idx++));
        }

        @Override
        public Result<Token> consume(Token token) {
            if (!hasNext()) return new IncorrectResult<>("Uncomplete tokens sequence");
            Token cur = tokens.get(idx);
            if (!cur.equals(token)) {
                return new IncorrectResult<>("Unexpected token");
            }
            idx++;
            return new CorrectResult<>(cur);
        }

        @Override
        public Result<Integer> consumeAll(Token token) {
            int count = 0;
            while (hasNext() && tokens.get(idx).equals(token)) {
                idx++;
                count++;
            }
            return new CorrectResult<>(count);
        }

        @Override
        public Token peek(int offset) {
            int pos = idx + offset;
            if (pos >= tokens.size()) return null;
            return tokens.get(pos);
        }

        @Override
        public void cleanBuffer() {
            // no-op for simple implementation
        }

        @Override
        public boolean hasNext() {
            return idx < tokens.size();
        }

        @Override
        public Token next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return tokens.get(idx++);
        }

        @Override
        public Token peek() {
            return peek(0);
        }
    }

    @Test
    void parseReturnsTypeWhenPresent() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token t = tf.createTypeToken("int", 1, 2);
        // a minimal TokenStream that returns our single token
        TokenStreamSimple itStream = new TokenStreamSimple(List.of(t));
        TypeParser parser = new TypeParser(new DefaultNodeFactory(), tf);
        Result<TypeNode> res = parser.parse(itStream);

        assertTrue(res.isCorrect());
        assertNotNull(res.result());
        assertEquals(1, res.result().line());
        assertEquals(2, res.result().column());
    }

    @Test
    void parseReturnsIncorrectWhenMissing() {
        DefaultTokensFactory tf = new DefaultTokensFactory();
        Token other = tf.createLiteralToken("x", 1, 1);
        TokenStreamSimple itStream = new TokenStreamSimple(List.of(other));
        TypeParser parser = new TypeParser(new DefaultNodeFactory(), tf);
        Result<TypeNode> res = parser.parse(itStream);

        assertFalse(res.isCorrect());
        assertNotNull(res.error());
    }
}
