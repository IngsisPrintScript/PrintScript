/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

class ValueAssignationParserTest {

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
            if (!cur.equals(token)) return new IncorrectResult<>("Unexpected token");
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
    void parseReturnsCorrectOnValidAssignation() {
        TokenFactory tf = new DefaultTokensFactory();
        Token id = tf.createIdentifierToken("x", 1, 1);
        Token eq = tf.createOperatorToken("=", 1, 2);
        Token lit = tf.createLiteralToken("1", 1, 3);

        TokenStreamSimple stream = new TokenStreamSimple(List.of(id, eq, lit));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        ValueAssignationParser p = pf.createValueAssignationParser();

        Result<?> res = p.parse(stream);
        assertTrue(res.isCorrect());
    }

    @Test
    void parseReturnsIncorrectWhenNoEquals() {
        TokenFactory tf = new DefaultTokensFactory();
        Token id = tf.createIdentifierToken("x", 1, 1);
        Token wrong = tf.createLiteralToken("y", 1, 2);

        TokenStreamSimple stream = new TokenStreamSimple(List.of(id, wrong));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        ValueAssignationParser p = pf.createValueAssignationParser();

        Result<?> res = p.parse(stream);
        assertFalse(res.isCorrect());
        assertNotNull(res.error());
    }
}
