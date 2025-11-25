/*
 * My Project
 */

package com.ingsis.syntactic.parsers.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class LiteralParserTest {

    private static final class StubStream implements TokenStream {
        private final Token token;
        private boolean consumed = false;

        StubStream(Token token) {
            this.token = token;
        }

        @Override
        public boolean match(Token tokenTemplate) {
            return this.token != null
                    && this.token.name().equals(tokenTemplate.name())
                    && !consumed;
        }

        @Override
        public Result<Token> consume() {
            if (consumed || token == null) return new IncorrectResult<>("no");
            consumed = true;
            return new CorrectResult<>(token);
        }

        @Override
        public Result<Token> consume(Token token) {
            return this.token == null
                    ? new IncorrectResult<>("no")
                    : new CorrectResult<>(this.token);
        }

        @Override
        public Result<Integer> consumeAll(Token token) {
            return new IncorrectResult<>("no");
        }

        @Override
        public Token peek(int offset) {
            return offset == 0 ? (consumed ? null : token) : null;
        }

        @Override
        public void cleanBuffer() {}

        @Override
        public boolean hasNext() {
            return !consumed && token != null;
        }

        @Override
        public Token next() {
            if (consumed) return null;
            consumed = true;
            return token;
        }

        @Override
        public Token peek() {
            return consumed ? null : token;
        }
    }

    @Test
    void parseLiteralProducesCorrectNode() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();
        Token token = tf.createLiteralToken("42", 1, 2);
        LiteralParser parser = new LiteralParser(tf, nf);
        StubStream stream = new StubStream(token);

        Result<LiteralNode> r = parser.parse(stream);
        assertTrue(r.isCorrect());
        LiteralNode node = r.result();
        assertEquals("42", node.value());
        assertEquals(1, node.line());
        assertEquals(2, node.column());
    }

    @Test
    void parseWhenNoLiteralReturnsIncorrect() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();
        LiteralParser parser = new LiteralParser(tf, nf);
        StubStream stream = new StubStream(null);

        Result<LiteralNode> r = parser.parse(stream);
        assertFalse(r.isCorrect());
    }
}
