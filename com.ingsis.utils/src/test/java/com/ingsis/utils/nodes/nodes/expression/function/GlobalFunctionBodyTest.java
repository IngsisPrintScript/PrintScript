/*
 * My Project
 */

package com.ingsis.nodes.expression.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GlobalFunctionBodyTest {

    private GlobalFunctionBody body;

    private static final com.ingsis.visitors.Interpreter GOOD_INTERPRETER =
            new com.ingsis.visitors.Interpreter() {
                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<Object> interpret(
                        com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                    return new com.ingsis.result.CorrectResult<>(new Object());
                }
            };

    private static final com.ingsis.visitors.Interpreter BAD_INTERPRETER =
            new com.ingsis.visitors.Interpreter() {
                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.IfKeywordNode ifKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<String> interpret(
                        com.ingsis.nodes.keyword.DeclarationKeywordNode declarationKeywordNode) {
                    return new com.ingsis.result.CorrectResult<>("ok");
                }

                @Override
                public com.ingsis.result.Result<Object> interpret(
                        com.ingsis.nodes.expression.ExpressionNode expressionNode) {
                    return new com.ingsis.result.IncorrectResult<>("err");
                }
            };

    @BeforeEach
    public void setUp() {
        body = new GlobalFunctionBody(List.of("a", "b"), args -> "ok", 1, 1);
    }

    @Test
    public void argNamesImmutableAndReturned() {
        assertEquals(2, body.argNames().size());
        assertEquals("a", body.argNames().get(0));
    }

    @Test
    public void acceptVisitorIsUnimplemented() {
        assertThrows(UnsupportedOperationException.class, () -> body.acceptVisitor(null));
    }

    @Test
    public void interpreterPathsAndSymbolAndChildren() {
        assertEquals(
                "Interpreted successfully.", body.acceptInterpreter(GOOD_INTERPRETER).result());
        assertEquals("err", body.acceptInterpreter(BAD_INTERPRETER).error());
        assertEquals("", body.symbol());
        assertEquals(0, body.children().size());
        assertEquals(Boolean.TRUE, body.isTerminalNode());
    }
}
