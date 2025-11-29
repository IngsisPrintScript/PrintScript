/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.expression;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.parser.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.utils.nodes.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class LineExpressionParserTest {

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
            throw new UnsupportedOperationException();
        }

        @Override
        public Result<Token> consume(Token token) {
            if (!hasNext()) return new IncorrectResult<>("Uncomplete");
            Token cur = tokens.get(idx);
            if (!cur.equals(token)) return new IncorrectResult<>("Unexpected");
            idx++;
            return new CorrectResult<>(cur);
        }

        @Override
        public Result<Integer> consumeAll(Token token) {
            int c = 0;
            while (hasNext() && tokens.get(idx).equals(token)) {
                idx++;
                c++;
            }
            return new CorrectResult<>(c);
        }

        @Override
        public Token peek(int offset) {
            int pos = idx + offset;
            if (pos >= tokens.size()) return null;
            return tokens.get(pos);
        }

        @Override
        public Token peek() {
            return peek(0);
        }

        @Override
        public void cleanBuffer() {}

        @Override
        public boolean hasNext() {
            return idx < tokens.size();
        }

        @Override
        public Token next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return tokens.get(idx++);
        }
    }

    @Test
    void parseSucceedsWhenEolPresent() {
        TokenFactory tf = new DefaultTokensFactory();
        Token lit = tf.createLiteralToken("1", 1, 1);
        Token eol = tf.createEndOfLineToken(";", 1, 2);

        TokenStreamSimple stream = new TokenStreamSimple(List.of(lit, eol));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        LineExpressionParser p = new LineExpressionParser(tf, pf);

        Result<?> res = p.parse(stream);
        assertTrue(res.isCorrect());
    }

    @Test
    void parseFailsWhenMissingEol() {
        TokenFactory tf = new DefaultTokensFactory();
        Token lit = tf.createLiteralToken("1", 1, 1);

        TokenStreamSimple stream = new TokenStreamSimple(List.of(lit));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        LineExpressionParser p = new LineExpressionParser(tf, pf);

        Result<?> res = p.parse(stream);
        assertFalse(res.isCorrect());
    }
}
