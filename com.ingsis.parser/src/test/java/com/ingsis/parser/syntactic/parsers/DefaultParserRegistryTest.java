/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokenstream.TokenStream;
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
        public Result<String> acceptVisitor(Visitor visitor) {
            return new IncorrectResult<>("no");
        }
    }

    private static final class DummyStream implements TokenStream {
        @Override
        public boolean match(Token tokenTemplate) {
            return false;
        }

        @Override
        public Result<Token> consume() {
            return new IncorrectResult<>("no");
        }

        @Override
        public Result<Token> consume(Token token) {
            return new IncorrectResult<>("no");
        }

        @Override
        public Result<Integer> consumeAll(Token token) {
            return new IncorrectResult<>("no");
        }

        @Override
        public Token peek(int offset) {
            return null;
        }

        @Override
        public void cleanBuffer() {}

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Token next() {
            return null;
        }

        @Override
        public Token peek() {
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
