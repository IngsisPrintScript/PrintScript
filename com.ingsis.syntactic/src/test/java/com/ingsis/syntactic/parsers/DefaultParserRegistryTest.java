/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.Node;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;
import org.junit.jupiter.api.Test;

class DefaultParserRegistryTest {

    private static final class DummyNode implements Node {
        @Override
        public Integer line() {
            return 1;
        }

        @Override
        public Integer column() {
            return 2;
        }

        @Override
        public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) {
            return new IncorrectResult<>("no");
        }
    }

    private static final class DummyStream implements TokenStream {
        @Override
        public boolean match(com.ingsis.tokens.Token tokenTemplate) {
            return false;
        }

        @Override
        public Result<com.ingsis.tokens.Token> consume() {
            return new IncorrectResult<>("no");
        }

        @Override
        public Result<com.ingsis.tokens.Token> consume(com.ingsis.tokens.Token token) {
            return new IncorrectResult<>("no");
        }

        @Override
        public Result<Integer> consumeAll(com.ingsis.tokens.Token token) {
            return new IncorrectResult<>("no");
        }

        @Override
        public com.ingsis.tokens.Token peek(int offset) {
            return null;
        }

        @Override
        public void cleanBuffer() {}

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public com.ingsis.tokens.Token next() {
            return null;
        }

        @Override
        public com.ingsis.tokens.Token peek() {
            return null;
        }
    }

    @Test
    void registrySelectsFirstCorrectParser() {
        DefaultParserRegistry<Node> registry = new DefaultParserRegistry<>();

        Parser<Node> p1 = (stream) -> new IncorrectResult<>("no");
        DummyNode node = new DummyNode();
        Parser<Node> p2 = (stream) -> new CorrectResult<>(node);

        registry.registerParser(p1);
        registry.registerParser(p2);

        Result<Node> res = registry.parse(new DummyStream());
        assertTrue(res.isCorrect());
        assertSame(node, res.result());
    }
}
