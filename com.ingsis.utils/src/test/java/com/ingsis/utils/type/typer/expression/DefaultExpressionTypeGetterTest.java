/*
 * My Project
 */

package com.ingsis.utils.type.typer.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.type.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultExpressionTypeGetterTest {

    private final DefaultRuntime runtime = DefaultRuntime.getInstance();

    @BeforeEach
    void push() {
        runtime.push();
    }

    @AfterEach
    void pop() {
        runtime.pop();
    }

    @Test
    void literalDelegatesToLiteralGetter() {
        LiteralNode lit = new LiteralNode("123", 1, 1);
        DefaultExpressionTypeGetter getter = new DefaultExpressionTypeGetter(runtime);
        assertEquals(Types.NUMBER, getter.getType(lit));
    }

    @Test
    void identifierDelegatesToIdentifierGetter() {
        runtime.getCurrentEnvironment().createVariable("v", Types.STRING, "ok");
        IdentifierNode id = new IdentifierNode("v", 1, 1);
        DefaultExpressionTypeGetter getter = new DefaultExpressionTypeGetter(runtime);
        assertEquals(Types.STRING, getter.getType(id));
    }

    @Test
    void callFunctionDelegatesToFunctionGetter() {
        runtime.getCurrentEnvironment().createFunction("g", java.util.Map.of(), Types.BOOLEAN);
        IdentifierNode idNode = new IdentifierNode("g", 1, 1);
        CallFunctionNode call = new CallFunctionNode(idNode, List.of(), 1, 1);
        DefaultExpressionTypeGetter getter = new DefaultExpressionTypeGetter(runtime);
        assertEquals(Types.BOOLEAN, getter.getType(call));
    }

    @Test
    void fallbackUsesFirstChild() {
        ExpressionNode custom = createCustomLiteralChild();

        DefaultExpressionTypeGetter getter = new DefaultExpressionTypeGetter(runtime);
        assertEquals(Types.BOOLEAN, getter.getType(custom));
    }

    private ExpressionNode createCustomLiteralChild() {
        return new ExpressionNode() {
            @Override
            public java.util.List<ExpressionNode> children() {
                return List.of(new LiteralNode("true", 1, 1));
            }

            @Override
            public Boolean isTerminalNode() {
                return false;
            }

            @Override
            public String symbol() {
                return "custom";
            }

            @Override
            public Result<String> acceptChecker(Checker checker) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Result<String> acceptVisitor(Visitor visitor) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Result<String> acceptInterpreter(Interpreter interpreter) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Integer line() {
                return 1;
            }

            @Override
            public Integer column() {
                return 1;
            }
        };
    }
}
