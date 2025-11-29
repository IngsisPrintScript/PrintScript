/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GlobalFunctionBodyTest {

    private GlobalFunctionBody body;

    private static final Interpreter GOOD_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    return new CorrectResult<>(new Object());
                }
            };

    private static final Interpreter BAD_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    return new IncorrectResult<>("err");
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
