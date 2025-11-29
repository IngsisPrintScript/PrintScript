/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Runtime is intentionally passed and stored; design needs mutability.")
public final class AssignationSolutionStrategy implements ExpressionSolutionStrategy {
    private final ExpressionSolutionStrategy nextStrategy;
    private final Runtime RUNTIME;

    public AssignationSolutionStrategy(Runtime runtime, ExpressionSolutionStrategy nextStrategy) {
        this.nextStrategy = nextStrategy;
        this.RUNTIME = runtime;
    }

    private boolean canSolve(ExpressionNode expressionNode) {
        if (expressionNode.isTerminalNode()) {
            return false;
        }
        String symbol = expressionNode.symbol();
        return symbol.equals("=");
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!canSolve(expressionNode)) {
            return nextStrategy.solve(interpreter, expressionNode);
        }
        List<ExpressionNode> children = expressionNode.children();
        IdentifierNode identifierNode = (IdentifierNode) children.get(0);
        ExpressionNode varNewExpression = (ExpressionNode) children.get(1);

        Result<Object> interpretChild = interpreter.interpret(varNewExpression);
        if (!interpretChild.isCorrect()) {
            return interpretChild;
        }
        Result<VariableEntry> getVariableEntryResult =
                RUNTIME.getCurrentEnvironment().readVariable(identifierNode.name());
        if (!getVariableEntryResult.isCorrect()) {
            return new IncorrectResult<>(getVariableEntryResult);
        }
        VariableEntry variableEntry = getVariableEntryResult.result();
        if (!variableEntry.type().isCompatibleWith(interpretChild.result())) {
            return new IncorrectResult<>("Value type does not matches expected type.");
        }
        RUNTIME.getCurrentEnvironment()
                .updateVariable(identifierNode.name(), interpretChild.result());
        return new CorrectResult<>(interpretChild.result());
    }
}
