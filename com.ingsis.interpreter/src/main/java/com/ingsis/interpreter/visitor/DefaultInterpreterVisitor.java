/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultInterpreterVisitor implements Interpreter {
    private final Runtime runtime;
    private final ExpressionSolutionStrategy expressionSolutionStrategy;

    public DefaultInterpreterVisitor(
            Runtime runtime, ExpressionSolutionStrategy expressionSolutionStrategy) {
        this.runtime = runtime;
        this.expressionSolutionStrategy = expressionSolutionStrategy;
    }

    @Override
    public Result<String> interpret(IfKeywordNode ifKeywordNode) {
        Result<Object> solveConditionResult = this.interpret(ifKeywordNode.condition());
        if (!solveConditionResult.isCorrect()) {
            return new IncorrectResult<>(solveConditionResult);
        }
        Object conditionValue = solveConditionResult.result();
        switch (conditionValue.toString()) {
            case "true":
                return interpretChildren(ifKeywordNode.thenBody());
            case "false":
                return interpretChildren(ifKeywordNode.elseBody());
        }
        return new IncorrectResult<>("Something went wrong.");
    }

    private Result<String> interpretChildren(List<Node> children) {
        for (Node child : children) {
            if (!(child instanceof Interpretable interpretableChild)) {
                return new IncorrectResult<>("Then child is not interpretable");
            }
            Result<String> interpretChildResult = interpretableChild.acceptInterpreter(this);
            if (!interpretChildResult.isCorrect()) {
                return new IncorrectResult<>(interpretChildResult);
            }
        }
        return new CorrectResult<>("Children interpreted correctly.");
    }

    private Result<String> declareVar(TypeAssignationNode typeAssignationNode) {
        String identifier = typeAssignationNode.identifierNode().name();
        Types type = typeAssignationNode.typeNode().type();
        Result<VariableEntry> declareVarResult =
                runtime.getCurrentEnvironment().createVariable(identifier, type);
        if (!declareVarResult.isCorrect()) {
            return new IncorrectResult<>(declareVarResult);
        }
        return new CorrectResult<>(
                "Variable " + identifier + " has been declared with type " + type.keyword());
    }

    private Result<String> initializeVal(ValueAssignationNode valueAssignationNode) {
        String identifier = valueAssignationNode.identifierNode().name();
        Result<Object> valueResult = this.interpret(valueAssignationNode.expressionNode());
        if (!valueResult.isCorrect()) {
            return new IncorrectResult<>(valueResult);
        }
        Object value = valueResult.result();
        Result<VariableEntry> getVariableEntryResult =
                runtime.getCurrentEnvironment().readVariable(identifier);
        if (!getVariableEntryResult.isCorrect()) {
            return new IncorrectResult<>(getVariableEntryResult);
        }
        VariableEntry variableEntry = getVariableEntryResult.result();
        if (!variableEntry.type().isCompatibleWith(value)) {
            runtime.getCurrentEnvironment().deleteVariable(identifier);
            return new IncorrectResult<>("Tried initializing a var with a non correct type value");
        }
        Result<VariableEntry> modifyVarResult =
                runtime.getCurrentEnvironment().updateVariable(identifier, value);
        if (!modifyVarResult.isCorrect()) {
            return new IncorrectResult<>(modifyVarResult);
        }
        return new CorrectResult<>("Variable " + identifier + " value was updated to " + value);
    }

    private Result<String> defineVar(DeclarationKeywordNode declarationKeywordNode) {
        Result<String> typeAssignationResult =
                declareVar(declarationKeywordNode.typeAssignationNode());
        if (!typeAssignationResult.isCorrect()) {
            return new IncorrectResult<>(typeAssignationResult);
        }
        Result<String> valueAssignationResult =
                initializeVal(declarationKeywordNode.valueAssignationNode());
        if (!valueAssignationResult.isCorrect()) {
            return new IncorrectResult<>(valueAssignationResult);
        }
        return new CorrectResult<>("New variable declared and initialized.");
    }

    private Result<String> defineVal(DeclarationKeywordNode declarationKeywordNode) {
        TypeAssignationNode typeAssignationNode = declarationKeywordNode.typeAssignationNode();
        Types type = typeAssignationNode.typeNode().type();
        String identifier = typeAssignationNode.identifierNode().name();
        ValueAssignationNode valueAssignationNode = declarationKeywordNode.valueAssignationNode();
        Result<Object> solveExpressionResult =
                this.interpret(valueAssignationNode.expressionNode());
        if (!solveExpressionResult.isCorrect()) {
            return new IncorrectResult<>(solveExpressionResult);
        }
        Object value = solveExpressionResult.result();
        Result<VariableEntry> getVariableEntryResult =
                runtime.getCurrentEnvironment().readVariable(identifier);
        if (!getVariableEntryResult.isCorrect()) {
            return new IncorrectResult<>(getVariableEntryResult);
        }
        VariableEntry variableEntry = getVariableEntryResult.result();
        if (!variableEntry.type().isCompatibleWith(value)) {
            return new IncorrectResult<>("Tried initializing a val with a non correct type value");
        }
        Result<VariableEntry> createConstResult =
                runtime.getCurrentEnvironment().createVariable(identifier, type, value);
        if (!createConstResult.isCorrect()) {
            return new IncorrectResult<>(createConstResult);
        }
        return new CorrectResult<>("New variable declared and initialized.");
    }

    @Override
    public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
        if (declarationKeywordNode.isMutable()) {
            return defineVar(declarationKeywordNode);
        } else {
            return defineVal(declarationKeywordNode);
        }
    }

    @Override
    public Result<Object> interpret(ExpressionNode expressionNode) {
        return expressionSolutionStrategy.solve(this, expressionNode);
    }
}
