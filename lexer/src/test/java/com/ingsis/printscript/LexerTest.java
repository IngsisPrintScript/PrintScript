package com.ingsis.printscript;

import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.tokenizers.factories.TokenizerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LexerTest {

    static class CodeRepository implements PeekableIterator<Character> {
        private int index = 0;
        private final String content;

        public CodeRepository(Path path) {
            try {
                this.content = Files.readString(path);
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file: " + path, e);
            }
        }
        public Character peek() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return content.charAt(index);
        }

        public boolean hasNext() {
            return content.length() > index;
        }

        public Character next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return content.charAt(index++);
        }
    }



    @Test
    void shouldReturnCorrectResultWhenHaveValidNodes() throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("test")).toURI());
        CodeRepository repo = new CodeRepository(path);
        Lexical lexical = new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);

        Assertions.assertTrue(lexical.hasNext(), "No hay tokens disponibles");
        Assertions.assertNotNull(lexical.peek());
    }

    @Test
    void shouldReturnIncorrectResultWhenNotHaveValidNodes() throws URISyntaxException {
        Lexical lexical = new Lexical(new TokenizerFactory().createDefaultTokenizer(), null);
        Assertions.assertInstanceOf(IncorrectResult.class, lexical.analyze("let a:aaa = aaa;"));
    }

    @Test
    void shouldReturnCorrectResultAllTokenizers() throws URISyntaxException {
        Lexical lexical = new Lexical(new TokenizerFactory().createDefaultTokenizer(), null);
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("let"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("f"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("Number"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("="));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("2"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze(";"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("String"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("println"));
        Assertions.assertInstanceOf(CorrectResult.class, lexical.analyze("+"));
    }
}
