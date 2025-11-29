/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ExpressionNodeInterfaceTest {

    @Test
    public void anonymousExpressionNodeBehaves() {
        ExpressionNode en =
                new ExpressionNode() {
                    @Override
                    public java.util.List<ExpressionNode> children() {
                        return List.of();
                    }

                    @Override
                    public Boolean isTerminalNode() {
                        return true;
                    }

                    @Override
                    public String symbol() {
                        return "s";
                    }

                    @Override
                    public Integer line() {
                        return 1;
                    }

                    @Override
                    public Integer column() {
                        return 2;
                    }

                    @Override
                    public Result<String> acceptChecker(Checker checker) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> acceptVisitor(Visitor visitor) {
                        return new CorrectResult<>("ok");
                    }

                    @Override
                    public Result<String> acceptInterpreter(Interpreter interpreter) {
                        return new CorrectResult<>("ok");
                    }
                };

        assertEquals("s", en.symbol());
        assertEquals(Boolean.TRUE, en.isTerminalNode());
    }
}
