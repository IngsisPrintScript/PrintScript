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
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
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

    private Result<String> declareVar(TypeAssignationNode typeAssignationNode) {
        String identifier = typeAssignationNode.identifierNode().name();
        Types type = typeAssignationNode.typeNode().type();
        Result<VariableEntry> declareVarResult =
                runtime.getCurrentEnvironment().createVariable(identifier, type);
        if (!declareVarResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(declareVarResult);
        }
        return resultFactory.createCorrectResult("Interpreted succesfully.");
    }

    private Result<String> initializeVal(ValueAssignationNode valueAssignationNode) {
        String identifier = valueAssignationNode.identifierNode().name();
        Result<Object> valueResult = this.interpret(valueAssignationNode.expressionNode());
        if (!valueResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(valueResult);
        }
        Object value = valueResult.result();
        Result<VariableEntry> getVariableEntryResult =
                runtime.getCurrentEnvironment().readVariable(identifier);
        if (!getVariableEntryResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(getVariableEntryResult);
        }
        VariableEntry variableEntry = getVariableEntryResult.result();
        if (!variableEntry.type().isCompatibleWith(value)) {
            runtime.getCurrentEnvironment().deleteVariable(identifier);
            return resultFactory.createIncorrectResult(
                    String.format(
                            "User provided an unexepcted type on line:%d and column:%d",
                            valueAssignationNode.line(), valueAssignationNode.line()));
        }
        Result<VariableEntry> modifyVarResult =
                runtime.getCurrentEnvironment().updateVariable(identifier, value);
        if (!modifyVarResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(modifyVarResult);
        }
        return resultFactory.createCorrectResult("Interpreted correctly.");
    }

    private Result<String> defineVar(DeclarationKeywordNode declarationKeywordNode) {
        Result<String> typeAssignationResult =
                declareVar(declarationKeywordNode.typeAssignationNode());
        if (!typeAssignationResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(typeAssignationResult);
        }
        Result<String> valueAssignationResult =
                initializeVal(declarationKeywordNode.valueAssignationNode());
        if (!valueAssignationResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(valueAssignationResult);
        }
        return resultFactory.createCorrectResult("Interpreted correctly.");
    }

    private Result<String> defineVal(DeclarationKeywordNode declarationKeywordNode) {
        TypeAssignationNode typeAssignationNode = declarationKeywordNode.typeAssignationNode();
        Types type = typeAssignationNode.typeNode().type();
        String identifier = typeAssignationNode.identifierNode().name();
        ValueAssignationNode valueAssignationNode = declarationKeywordNode.valueAssignationNode();
        Result<Object> solveExpressionResult =
                this.interpret(valueAssignationNode.expressionNode());
        if (!solveExpressionResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(solveExpressionResult);
        }
        Object value = solveExpressionResult.result();
        Result<VariableEntry> getVariableEntryResult =
                runtime.getCurrentEnvironment().readVariable(identifier);
        if (getVariableEntryResult.isCorrect()) {
            return resultFactory.createIncorrectResult(
                    String.format(
                            "Tried to redeclarate an already declarated variable on line: %d and"
                                    + " column: %d.",
                            declarationKeywordNode.line(), declarationKeywordNode.column()));
        }
        Result<VariableEntry> createConstResult =
                runtime.getCurrentEnvironment().createVariable(identifier, type, value);
        if (!createConstResult.isCorrect()) {
            return resultFactory.cloneIncorrectResult(createConstResult);
        }
        return resultFactory.createCorrectResult("Interpreted correctly.");
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
