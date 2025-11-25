/*
 * My Project
 */

package com.ingsis.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.lexer.tokenizers.factories.SecondTokenizerFactory;
import com.ingsis.metachar.MetaChar;
import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultLexerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
    }

    @Test
    void iterateTokensSequencePeekNextHasNext() {
        Queue<Character> chars = new LinkedList<>();
        String input = "let x;";
        for (char c : input.toCharArray()) {
            chars.add(c);
        }
        PeekableIterator<MetaChar> charStream = new ListPeekableCharIterator(chars);
        Tokenizer tokenizer =
                new SecondTokenizerFactory(tokenFactory, resultFactory).createTokenizer();
        DefaultLexer lexer = new DefaultLexer(charStream, tokenizer, resultFactory);

        assertTrue(lexer.hasNext());
        Token first = lexer.peek();
        assertNotNull(first);
        Token t1 = lexer.next();
        assertEquals(first, t1);

        // consume remaining tokens
        int count = 1;
        while (lexer.hasNext()) {
            lexer.next();
            count++;
        }
        assertTrue(count >= 3);

        assertFalse(lexer.hasNext());
        assertThrows(NoSuchElementException.class, lexer::peek);
        assertThrows(NoSuchElementException.class, lexer::next);
    }

    @Test
    void analyzeDelegatesToTokenizer() {
        Queue<Character> chars = new LinkedList<>();
        for (char c : "123".toCharArray()) chars.add(c);
        PeekableIterator<MetaChar> charStream = new ListPeekableCharIterator(chars);
        Tokenizer tokenizer =
                new SecondTokenizerFactory(tokenFactory, resultFactory).createTokenizer();
        DefaultLexer lexer = new DefaultLexer(charStream, tokenizer, resultFactory);

        assertTrue(lexer.hasNext());
        Token t = lexer.next();
        assertEquals("LITERAL_TOKEN", t.name());
    }
}

class ListPeekableCharIterator implements PeekableIterator<MetaChar> {
    private final Queue<Character> buffer;
    private Integer currentLine = 1;
    private Integer currentColumn = 1;

    public ListPeekableCharIterator(Queue<Character> buffer) {
        this.buffer = new LinkedList<>(buffer);
    }

    @Override
    public MetaChar peek() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        return new MetaChar(buffer.peek(), currentLine, currentColumn);
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty();
    }

    @Override
    public MetaChar next() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        Character c = buffer.poll();
        MetaChar result = new MetaChar(c, currentLine, currentColumn);
        if (c == '\n') {
            currentColumn = 0;
            currentLine++;
        } else {
            currentColumn++;
        }
        return result;
    }
}
