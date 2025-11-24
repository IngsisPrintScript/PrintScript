/*
 * My Project
 */

package com.ingsis.syntactic.parsers.identifier;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
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

class IdentifierParserTest {

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
    void parseIdentifierSuccess() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();
        Token token = tf.createIdentifierToken("x", 5, 6);
        IdentifierParser parser = new IdentifierParser(tf, nf);
        StubStream stream = new StubStream(token);

        Result<IdentifierNode> r = parser.parse(stream);
        assertTrue(r.isCorrect());
        IdentifierNode node = r.result();
        assertEquals("x", node.name());
        assertEquals(5, node.line());
        assertEquals(6, node.column());
    }

    @Test
    void parseIdentifierFailure() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();
        IdentifierParser parser = new IdentifierParser(tf, nf);
        StubStream stream = new StubStream(null);

        Result<IdentifierNode> r = parser.parse(stream);
        assertFalse(r.isCorrect());
    }
}
