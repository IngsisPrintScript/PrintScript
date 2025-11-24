/*
 * My Project
 */

package com.ingsis.syntactic.parsers.conditional;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.factories.DefaultNodeFactory;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConditionalParserTest {

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
    void parseIfWithoutElse() {
        TokenFactory tf = new DefaultTokensFactory();
        Token iff = tf.createKeywordToken("if", 1, 1);
        Token lpar = tf.createSeparatorToken("(", 1, 2);
        Token lit = tf.createLiteralToken("1", 1, 3);
        Token rpar = tf.createSeparatorToken(")", 1, 4);
        Token lbrace = tf.createSeparatorToken("{", 1, 5);
        Token lit2 = tf.createLiteralToken("2", 1, 6);
        Token eol = tf.createEndOfLineToken(";", 1, 7);
        Token rbrace = tf.createSeparatorToken("}", 1, 8);

        TokenStreamSimple stream =
                new TokenStreamSimple(List.of(iff, lpar, lit, rpar, lbrace, lit2, eol, rbrace));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        ConditionalParser p =
                new ConditionalParser(
                        tf, pf, new DefaultNodeFactory(), new DefaultParserChainFactory(pf));

        Result<?> res = p.parse(stream);
        assertTrue(res.isCorrect());
    }

    @Test
    void parseIfWithElse() {
        TokenFactory tf = new DefaultTokensFactory();
        Token iff = tf.createKeywordToken("if", 1, 1);
        Token lpar = tf.createSeparatorToken("(", 1, 2);
        Token lit = tf.createLiteralToken("1", 1, 3);
        Token rpar = tf.createSeparatorToken(")", 1, 4);
        Token lbrace = tf.createSeparatorToken("{", 1, 5);
        Token lit2 = tf.createLiteralToken("2", 1, 6);
        Token eol = tf.createEndOfLineToken(";", 1, 7);
        Token rbrace = tf.createSeparatorToken("}", 1, 8);
        Token elseTok = tf.createKeywordToken("else", 1, 9);
        Token lbrace2 = tf.createSeparatorToken("{", 1, 10);
        Token lit3 = tf.createLiteralToken("3", 1, 11);
        Token eol2 = tf.createEndOfLineToken(";", 1, 12);
        Token rbrace2 = tf.createSeparatorToken("}", 1, 13);

        TokenStreamSimple stream =
                new TokenStreamSimple(
                        List.of(
                                iff, lpar, lit, rpar, lbrace, lit2, eol, rbrace, elseTok, lbrace2,
                                lit3, eol2, rbrace2));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        ConditionalParser p =
                new ConditionalParser(
                        tf, pf, new DefaultNodeFactory(), new DefaultParserChainFactory(pf));

        Result<?> res = p.parse(stream);
        assertTrue(res.isCorrect());
    }
}
