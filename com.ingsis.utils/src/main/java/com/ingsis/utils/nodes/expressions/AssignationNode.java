/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.nodes.expressions.atomic.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import java.util.List;

public record AssignationNode(
        IdentifierNode identifierNode, ExpressionNode expressionNode, Integer line, Integer column)
        implements ExpressionNode {
    @Override
    public List<ExpressionNode> children() {
        return List.of(identifierNode(), expressionNode());
    }

    @Override
    public Result<Object> solve() {
        Result<Object> getValue = expressionNode.solve();
        if (!getValue.isCorrect()) {
            return new IncorrectResult<>(getValue);
        }
        Object value = getValue.result();
        Result<VariableEntry> updateVariable =
                DefaultRuntime.getInstance()
                        .getCurrentEnvironment()
                        .updateVariable(identifierNode.name(), value);
        if (!updateVariable.isCorrect()) {
            return new IncorrectResult<>(updateVariable);
        }
        return new CorrectResult<>(null);
    }

    @Override
    public String symbol() {
        return "=";
    }

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<Object> result = interpreter.interpret(this);
        if (!result.isCorrect()) {
            return new IncorrectResult<>(result);
        }
        return new CorrectResult<>("Correctly interpreted");
    }
}
