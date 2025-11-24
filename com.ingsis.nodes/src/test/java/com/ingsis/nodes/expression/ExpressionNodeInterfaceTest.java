package com.ingsis.nodes.expression;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionNodeInterfaceTest {

    @Test
    public void anonymousExpressionNodeBehaves() {
        ExpressionNode en = new ExpressionNode() {
            @Override
            public java.util.List<ExpressionNode> children() { return List.of(); }

            @Override
            public Boolean isTerminalNode() { return true; }

            @Override
            public String symbol() { return "s"; }

            @Override
            public Integer line() { return 1; }

            @Override
            public Integer column() { return 2; }

            @Override
            public Result<String> acceptChecker(Checker checker) { return new CorrectResult<>("ok"); }

            @Override
            public Result<String> acceptVisitor(Visitor visitor) { return new CorrectResult<>("ok"); }

            @Override
            public Result<String> acceptInterpreter(Interpreter interpreter) { return new CorrectResult<>("ok"); }
        };

        assertEquals("s", en.symbol());
        assertEquals(Boolean.TRUE, en.isTerminalNode());
    }
}
