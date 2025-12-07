/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultInterpreterVisitor implements Interpreter {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public DefaultInterpreterVisitor(Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
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
        return expressionNode.solve();
    }
}
