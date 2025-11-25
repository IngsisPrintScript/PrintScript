/*
 * My Project
 */

package com.ingsis.syntactic.parsers.declaration;

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

class DeclarationParserTest {

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
    void parseLetDeclaration() {
        TokenFactory tf = new DefaultTokensFactory();
        Token let = tf.createKeywordToken("let", 1, 1);
        Token id = tf.createIdentifierToken("x", 1, 2);
        Token colon = tf.createOperatorToken(":", 1, 3);
        Token type = tf.createTypeToken("int", 1, 4);
        Token eq = tf.createOperatorToken("=", 1, 5);
        Token lit = tf.createLiteralToken("1", 1, 6);
        Token eol = tf.createEndOfLineToken(";", 1, 7);

        TokenStreamSimple stream =
                new TokenStreamSimple(List.of(let, id, colon, type, eq, lit, eol));
        ParserFactory pf = new DefaultParserFactory(tf, new DefaultNodeFactory());
        DeclarationParser p = new DeclarationParser(tf, pf, new DefaultNodeFactory());

        Result<?> res = p.parse(stream);
        assertTrue(res.isCorrect());
    }
}
