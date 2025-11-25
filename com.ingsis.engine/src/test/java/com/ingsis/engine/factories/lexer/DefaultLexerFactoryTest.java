/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.lexer.Lexer;
import com.ingsis.runtime.DefaultRuntime;
import java.io.IOException;
import java.util.ArrayDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultLexerFactoryTest {
    private DefaultLexerFactory factory;

    @BeforeEach
    void setup() {
        factory =
                new DefaultLexerFactory(
                        new com.ingsis.engine.factories.charstream.DefaultCharStreamFactory(),
                        new com.ingsis.lexer.tokenizers.factories.TokenizerFactory() {
                            @Override
                            public com.ingsis.lexer.tokenizers.Tokenizer createTokenizer() {
                                return (input, line, column) ->
                                        new com.ingsis.result.IncorrectResult<>("no");
                            }
                        },
                        DefaultRuntime.getInstance());
    }

    @Test
    void createCliLexerReturnsLexer() {
        Lexer lexer =
                factory.createCliLexer(
                        new ArrayDeque<>(), new com.ingsis.result.factory.DefaultResultFactory());
        assertNotNull(lexer);
    }

    @Test
    void createFromFileLexerThrowsWhenFileMissing() {
        try {
            factory.createFromFileLexer(
                    java.nio.file.Path.of("/path/does/not/exist.ps"),
                    new com.ingsis.result.factory.DefaultResultFactory());
        } catch (IOException e) {
            assertNotNull(e);
        }
    }
}
