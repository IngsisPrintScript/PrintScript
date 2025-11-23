/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.result.factory.DefaultResultFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultTokenStreamFactoryTest {
    private DefaultTokenStreamFactory factory;

    @BeforeEach
    void setup() {
        factory =
                new DefaultTokenStreamFactory(
                        new com.ingsis.engine.factories.lexer.LexerFactory() {
                            @Override
                            public com.ingsis.lexer.Lexer createCliLexer(
                                    Queue<Character> buffer,
                                    com.ingsis.result.factory.ResultFactory resultFactory) {
                                return new com.ingsis.lexer.Lexer() {
                                    @Override
                                    public boolean hasNext() {
                                        return false;
                                    }

                                    @Override
                                    public com.ingsis.tokens.Token peek() {
                                        return null;
                                    }

                                    @Override
                                    public com.ingsis.tokens.Token next() {
                                        return null;
                                    }

                                    @Override
                                    public com.ingsis.result.Result<com.ingsis.tokens.Token>
                                            analyze(
                                                    com.ingsis.metachar.string.builder
                                                                    .MetaCharStringBuilder
                                                            stringBuilder) {
                                        return new com.ingsis.result.IncorrectResult<>("no");
                                    }
                                };
                            }

                            @Override
                            public com.ingsis.lexer.Lexer createFromFileLexer(
                                    Path filePath,
                                    com.ingsis.result.factory.ResultFactory resultFactory)
                                    throws IOException {
                                return createCliLexer(new ArrayDeque<>(), resultFactory);
                            }
                        },
                        new DefaultResultFactory());
    }

    @Test
    void createCliTokenStreamReturnsNotNull() {
        com.ingsis.tokenstream.TokenStream ts = factory.createCliTokenStream(new ArrayDeque<>());
        assertNotNull(ts);
    }

    @Test
    void createFileTokenStreamReturnsNotNull() throws IOException {
        com.ingsis.tokenstream.TokenStream ts = factory.createFileTokenStream(Path.of("/tmp/x"));
        assertNotNull(ts);
    }
}
