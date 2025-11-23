package com.ingsis.engine.factories.lexer;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.lexer.Lexer;
import java.io.IOException;
import java.util.Queue;
import java.util.ArrayDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultLexerFactoryTest {
    private DefaultLexerFactory factory;

    @BeforeEach
    void setup() {
        factory = new DefaultLexerFactory(
                new com.ingsis.engine.factories.charstream.DefaultCharStreamFactory(),
                new com.ingsis.lexer.tokenizers.factories.TokenizerFactory() {
                    @Override
                    public com.ingsis.lexer.tokenizers.Tokenizer createTokenizer() {
                        return (input, line, column) -> new com.ingsis.result.IncorrectResult<>("no");
                    }
                },
                DefaultRuntime.getInstance());
    }

    @Test
    void createCliLexerReturnsLexer() {
        Lexer lexer = factory.createCliLexer(new ArrayDeque<>(), new com.ingsis.result.factory.DefaultResultFactory());
        assertNotNull(lexer);
    }

    @Test
    void createFromFileLexerThrowsWhenFileMissing() {
        try {
            factory.createFromFileLexer(java.nio.file.Path.of("/path/does/not/exist.ps"), new com.ingsis.result.factory.DefaultResultFactory());
        } catch (IOException e) {
            assertNotNull(e);
        }
    }
}
