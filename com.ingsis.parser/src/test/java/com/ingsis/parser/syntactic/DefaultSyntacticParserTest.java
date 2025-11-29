/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class DefaultSyntacticParserTest {

    private static final class TestNode implements Node, Checkable {
        private final int line;
        private final int column;

        TestNode(int line, int column) {
            this.line = line;
            this.column = column;
        }

        @Override
        public Integer line() {
            return line;
        }

        @Override
        public Integer column() {
            return column;
        }

        @Override
        public Result<String> acceptChecker(Checker checker) {
            return new IncorrectResult<>("not-checked");
        }

        @Override
        public Result<String> acceptVisitor(Visitor visitor) {
            return new IncorrectResult<>("not-visited");
        }
    }

    private static final class DummyTokenStream implements TokenStream {
        @Override
        public boolean match(Token tokenTemplate) {
            return false;
        }

        @Override
        public Result<Token> consume() {
            return new IncorrectResult<>("no-token");
        }

        @Override
        public Result<Token> consume(Token token) {
            return new IncorrectResult<>("no-token");
        }

        @Override
        public Result<Integer> consumeAll(Token token) {
            return new IncorrectResult<>("no-token");
        }

        @Override
        public Token peek(int offset) {
            return null;
        }

        @Override
        public void cleanBuffer() {
            // no-op for tests
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Token next() {
            throw new NoSuchElementException();
        }

        @Override
        public Token peek() {
            return null;
        }
    }

    private static final class StubParser implements Parser<Node> {
        private final Result<? extends Node>[] results;
        private int idx = 0;

        @SafeVarargs
        StubParser(Result<? extends Node>... results) {
            this.results = results;
        }

        @Override
        public Result<Node> parse(TokenStream stream) {
            if (idx >= results.length) {
                return new IncorrectResult<>("no-more");
            }
            //noinspection unchecked
            return (Result<Node>) results[idx++];
        }
    }

    @Test
    void parseDelegatesToParser() {
        TestNode node = new TestNode(1, 2);
        StubParser parser = new StubParser(new CorrectResult<>(node));
        DefaultSyntacticParser sut = new DefaultSyntacticParser(new DummyTokenStream(), parser);

        Result<? extends Node> r = sut.parse();
        assertTrue(r.isCorrect());
        assertSame(node, r.result());
    }

    @Test
    void hasNextPeekNextBehavior() {
        TestNode node = new TestNode(3, 4);
        StubParser parser = new StubParser(new CorrectResult<>(node), new IncorrectResult<>("err"));
        DefaultSyntacticParser sut = new DefaultSyntacticParser(new DummyTokenStream(), parser);

        assertTrue(sut.hasNext());
        Checkable p = sut.peek();
        assertNotNull(p);
        Checkable n = sut.next();
        assertSame(p, n);

        // now parser will return IncorrectResult -> hasNext false
        assertFalse(sut.hasNext());
        assertThrows(NoSuchElementException.class, sut::next);
        assertThrows(NoSuchElementException.class, sut::peek);
    }

    @Test
    void peekThrowsWhenEmpty() {
        StubParser parser = new StubParser(new IncorrectResult<>("err"));
        DefaultSyntacticParser sut = new DefaultSyntacticParser(new DummyTokenStream(), parser);
        assertFalse(sut.hasNext());
        assertThrows(NoSuchElementException.class, sut::peek);
    }
}
