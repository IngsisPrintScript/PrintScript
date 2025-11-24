/*
 * My Project
 */

package com.ingsis.typer.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.types.Types;
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
        // create a custom ExpressionNode that isn't identifier/literal/call and whose first child
        // is a literal
        ExpressionNode custom =
                new ExpressionNode() {
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
                    public com.ingsis.result.Result<String> acceptChecker(
                            com.ingsis.visitors.Checker checker) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public com.ingsis.result.Result<String> acceptVisitor(
                            com.ingsis.visitors.Visitor visitor) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public com.ingsis.result.Result<String> acceptInterpreter(
                            com.ingsis.visitors.Interpreter interpreter) {
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

        DefaultExpressionTypeGetter getter = new DefaultExpressionTypeGetter(runtime);
        assertEquals(Types.BOOLEAN, getter.getType(custom));
    }
}
