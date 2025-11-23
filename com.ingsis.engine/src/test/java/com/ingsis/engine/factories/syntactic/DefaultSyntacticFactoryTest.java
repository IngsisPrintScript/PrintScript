/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.syntactic.SyntacticParser;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultSyntacticFactoryTest {
    private DefaultSyntacticFactory factory;

    @BeforeEach
    void setup() {
        factory =
                new DefaultSyntacticFactory(
                        new com.ingsis.engine.factories.tokenstream.TokenStreamFactory() {
                            @Override
                            public com.ingsis.tokenstream.TokenStream createCliTokenStream(
                                    Queue<Character> buffer) {
                                return new com.ingsis.tokenstream.TokenStream() {
                                    @Override
                                    public com.ingsis.result.Result<com.ingsis.tokens.Token>
                                            consume() {
                                        return new com.ingsis.result.IncorrectResult<>("no");
                                    }

                                    @Override
                                    public com.ingsis.result.Result<com.ingsis.tokens.Token>
                                            consume(com.ingsis.tokens.Token tokenTemplate) {
                                        return new com.ingsis.result.IncorrectResult<>("no");
                                    }

                                    @Override
                                    public com.ingsis.result.Result<Integer> consumeAll(
                                            com.ingsis.tokens.Token token) {
                                        return new com.ingsis.result.CorrectResult<>(0);
                                    }

                                    @Override
                                    public boolean match(com.ingsis.tokens.Token tokenTemplate) {
                                        return false;
                                    }

                                    @Override
                                    public com.ingsis.tokens.Token peek() {
                                        return null;
                                    }

                                    @Override
                                    public boolean hasNext() {
                                        return false;
                                    }

                                    @Override
                                    public com.ingsis.tokens.Token next() {
                                        return null;
                                    }

                                    @Override
                                    public com.ingsis.tokens.Token peek(int offset) {
                                        return null;
                                    }

                                    @Override
                                    public void cleanBuffer() {}
                                };
                            }

                            @Override
                            public com.ingsis.tokenstream.TokenStream createFileTokenStream(
                                    Path filePath) throws IOException {
                                return createCliTokenStream(new ArrayDeque<>());
                            }
                        },
                        new com.ingsis.syntactic.factories.ParserChainFactory() {
                            @Override
                            public com.ingsis.syntactic.parsers.Parser<com.ingsis.nodes.Node>
                                    createDefaultChain() {
                                return new com.ingsis.syntactic.parsers.Parser<
                                        com.ingsis.nodes.Node>() {
                                    @Override
                                    public com.ingsis.result.Result<com.ingsis.nodes.Node> parse(
                                            com.ingsis.tokenstream.TokenStream ts) {
                                        return new com.ingsis.result.IncorrectResult<>("no");
                                    }
                                };
                            }

                            @Override
                            public com.ingsis.syntactic.parsers.Parser<
                                            com.ingsis.nodes.expression.ExpressionNode>
                                    createExpressionChain() {
                                return new com.ingsis.syntactic.parsers.Parser<
                                        com.ingsis.nodes.expression.ExpressionNode>() {
                                    @Override
                                    public com.ingsis.result.Result<
                                                    com.ingsis.nodes.expression.ExpressionNode>
                                            parse(com.ingsis.tokenstream.TokenStream ts) {
                                        return new com.ingsis.result.IncorrectResult<>("no");
                                    }
                                };
                            }
                        });
    }

    @Test
    void createCliSyntacticCheckerReturnsNotNull() {
        SyntacticParser p = factory.createCliSyntacticChecker(new ArrayDeque<>());
        assertNotNull(p);
    }

    @Test
    void createFileSyntacticCheckerReturnsNotNull() throws IOException {
        SyntacticParser p = factory.createFileSyntacticChecker(Path.of("/tmp/x"));
        assertNotNull(p);
    }
}
