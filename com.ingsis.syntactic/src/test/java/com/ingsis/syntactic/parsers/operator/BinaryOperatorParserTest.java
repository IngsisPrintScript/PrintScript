/*
 * My Project
 */

package com.ingsis.syntactic.parsers.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.factories.DefaultNodeFactory;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;
import org.junit.jupiter.api.Test;

class BinaryOperatorParserTest {

    private static final class StubStream implements TokenStream {
        private final Token opToken;
        private final boolean match;
        private boolean consumed = false;

        StubStream(Token opToken, boolean match) {
            this.opToken = opToken;
            this.match = match;
        }

        @Override
        public boolean match(Token tokenTemplate) {
            return match && !consumed;
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
            if (consumed) return null;
            consumed = true;
            return opToken;
        }

        @Override
        public Token peek() {
            return consumed ? null : opToken;
        }
    }

    private static final class LeafParser
            implements com.ingsis.syntactic.parsers.Parser<ExpressionNode> {
        private final LiteralNode node;

        LeafParser(LiteralNode node) {
            this.node = node;
        }

        @Override
        public Result<ExpressionNode> parse(TokenStream stream) {
            return new CorrectResult<>(node);
        }
    }

    @Test
    void parseWithoutOperatorReturnsLeftChild() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();
        LiteralNode lit = nf.createLiteralNode("1", 1, 1);
        BinaryOperatorParser parser = new BinaryOperatorParser(nf, tf, new LeafParser(lit));
        StubStream stream = new StubStream(null, false);

        Result<ExpressionNode> r = parser.parse(stream);
        assertTrue(r.isCorrect());
        assertSame(lit, r.result());
    }

    @Test
    void parseWithOperatorCreatesBinaryNode() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();
        LiteralNode left = nf.createLiteralNode("1", 1, 1);
        LiteralNode right = nf.createLiteralNode("2", 1, 2);
        Token op = tf.createOperatorToken("+", 1, 5);
        com.ingsis.syntactic.parsers.Parser<ExpressionNode> alternatingParser =
                new com.ingsis.syntactic.parsers.Parser<ExpressionNode>() {
                    private int called = 0;

                    @Override
                    public Result<ExpressionNode> parse(TokenStream stream) {
                        // first call returns left, second call returns right
                        if (called++ == 0) {
                            return new CorrectResult<>(left);
                        }
                        return new CorrectResult<>(right);
                    }
                };

        BinaryOperatorParser parser = new BinaryOperatorParser(nf, tf, alternatingParser);

        StubStream stream = new StubStream(op, true);
        Result<ExpressionNode> r = parser.parse(stream);
        assertTrue(r.isCorrect());
        assertNotNull(r.result());
        assertEquals(5, r.result().column());
    }
}
