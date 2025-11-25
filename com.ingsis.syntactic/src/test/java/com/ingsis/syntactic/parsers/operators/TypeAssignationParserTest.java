/*
 * My Project
 */

package com.ingsis.syntactic.parsers.operators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.factories.DefaultNodeFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TypeAssignationParserTest {

    // no additional peekable needed; TokenStreamSimple is used directly
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
            // no-op
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
    void parseReturnsCorrectOnValidSequence() {
        TokenFactory tf = new DefaultTokensFactory();
        Token id = tf.createIdentifierToken("x", 1, 1);
        Token colon = tf.createOperatorToken(":", 1, 2);
        Token type = tf.createTypeToken("int", 1, 3);

        TokenStreamSimple stream = new TokenStreamSimple(List.of(id, colon, type));

        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        TypeAssignationParser p = pf.createTypeAssignationParser();

        Result<?> res = p.parse(stream);
        assertTrue(res.isCorrect());
    }

    @Test
    void parseReturnsIncorrectWhenMissingOperator() {
        TokenFactory tf = new DefaultTokensFactory();
        Token id = tf.createIdentifierToken("x", 1, 1);
        Token wrong = tf.createLiteralToken("v", 1, 2);
        TokenStreamSimple stream = new TokenStreamSimple(List.of(id, wrong));

        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        TypeAssignationParser p = pf.createTypeAssignationParser();

        Result<?> res = p.parse(stream);
        assertFalse(res.isCorrect());
        assertNotNull(res.error());
    }
}
