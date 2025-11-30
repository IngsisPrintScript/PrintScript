/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultInterpreterVisitor implements Interpreter {
    private final Runtime runtime;
    private final ExpressionSolutionStrategy expressionSolutionStrategy;
    private final ResultFactory resultFactory;

    public DefaultInterpreterVisitor(
            Runtime runtime,
            ExpressionSolutionStrategy expressionSolutionStrategy,
            ResultFactory resultFactory) {
        this.runtime = runtime;
        this.expressionSolutionStrategy = expressionSolutionStrategy;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> interpret(IfKeywordNode ifKeywordNode) {
        Result<Object> solveConditionResult = this.interpret(ifKeywordNode.condition());
        if (!solveConditionResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(solveConditionResult);
        }
        Object conditionValue = solveConditionResult.result();
        switch (conditionValue.toString()) {
            case "true":
                return interpretChildren(ifKeywordNode.thenBody());
            case "false":
                return interpretChildren(ifKeywordNode.elseBody());
        }
        return resultFactory.createIncorrectResult(
                String.format(
                        "Something went wrong on line: %d and column:%d.",
                        ifKeywordNode.condition().line(), ifKeywordNode.condition().column()));
    }

    private Result<String> interpretChildren(List<Node> children) {
        for (Node child : children) {
            if (!(child instanceof Interpretable interpretableChild)) {
                return resultFactory.createIncorrectResult(
                        String.format(
                                "Unable to interpret node on line:%d and column:%d",
                                child.line(), child.column()));
            }
            Result<String> interpretChildResult = interpretableChild.acceptInterpreter(this);
            if (!interpretChildResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(interpretChildResult);
            }
        }
        return resultFactory.createCorrectResult("Children interpreted correctly.");
    }

    @Override
    public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
        Result<Object> evaluateExpressionResult =
                this.interpret(declarationKeywordNode.expressionNode());
        if (!evaluateExpressionResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(evaluateExpressionResult);
        }
        Object value = evaluateExpressionResult.result();
        Result<VariableEntry> declareVarResult =
                runtime.getCurrentEnvironment()
                        .createVariable(
                                declarationKeywordNode.identifierNode().name(),
                                declarationKeywordNode.declaredType(),
                                value,
                                declarationKeywordNode.isMutable());
        if (!declareVarResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(declareVarResult);
        }

        return resultFactory.createCorrectResult("Correctly interpreted declaration.");
    }

    @Override
    public Result<Object> interpret(ExpressionNode expressionNode) {
        return expressionSolutionStrategy.solve(this, expressionNode);
    }
}
