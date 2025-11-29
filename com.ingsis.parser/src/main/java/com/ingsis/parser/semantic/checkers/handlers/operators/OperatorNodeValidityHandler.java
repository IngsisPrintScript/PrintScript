/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.operators;

import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.type.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.runtime.type.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.runtime.type.typer.identifier.DefaultIdentifierTypeGetter;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.OperatorNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.typer.literal.DefaultLiteralTypeGetter;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class OperatorNodeValidityHandler implements NodeEventHandler<ExpressionNode> {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public OperatorNodeValidityHandler(Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        if (node instanceof OperatorNode operatorNode) {
            if (operatorNode.symbol().equals("+")) {
                return resultFactory.createCorrectResult("Check passed");
            }
            Types expectedType =
                    new DefaultExpressionTypeGetter(runtime)
                            .getType(operatorNode.children().get(0));
            return recursiveCheck(expectedType, node);
        }

        return resultFactory.createCorrectResult("Check passed.");
    }

    private Result<String> recursiveCheck(Types expectedType, ExpressionNode node) {
        if (node instanceof LiteralNode literalNode) {
            return checkLiteral(expectedType, literalNode);
        } else if (node instanceof IdentifierNode identifierNode) {
            return checkIdentifier(expectedType, identifierNode);
        } else if (node instanceof CallFunctionNode callFunctionNode) {
            return checkFunction(expectedType, callFunctionNode);
        }

        for (ExpressionNode child : node.children()) {
            Result<String> checkTypeResult = recursiveCheck(expectedType, child);
            if (!checkTypeResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(checkTypeResult);
            }
        }

        return resultFactory.createCorrectResult("Check passed.");
    }

    private Result<String> checkLiteral(Types expectedType, LiteralNode literalNode) {
        boolean checkResult =
                new DefaultLiteralTypeGetter().getType(literalNode).isCompatibleWith(expectedType);
        if (checkResult) {
            return resultFactory.createCorrectResult("Check passed.");
        }
        return resultFactory.createIncorrectResult(
                String.format(
                        "Literal:%s has an unexpected type on line:%d and column:%d",
                        literalNode.value(), literalNode.line(), literalNode.column()));
    }

    private Result<String> checkIdentifier(Types expectedType, IdentifierNode identifierNode) {
        boolean checkResult =
                new DefaultIdentifierTypeGetter(runtime)
                        .getType(identifierNode)
                        .isCompatibleWith(expectedType);
        if (checkResult) {
            return resultFactory.createCorrectResult("Check passed.");
        }
        return resultFactory.createIncorrectResult(
                String.format(
                        "Identifier:%s has an unexpected type on line:%d and column:%d",
                        identifierNode.name(), identifierNode.line(), identifierNode.column()));
    }

    private Result<String> checkFunction(Types expectedType, CallFunctionNode callFunctionNode) {
        boolean check =
                new DefaultFunctionTypeGetter(runtime)
                        .getType(callFunctionNode)
                        .isCompatibleWith(expectedType);
        if (check) {
            return resultFactory.createCorrectResult("Check passed.");
        }
        return resultFactory.createIncorrectResult(
                String.format(
                        "Function: %s does not return correct type on line:%d and column:%d",
                        callFunctionNode.identifierNode().name(),
                        callFunctionNode.line(),
                        callFunctionNode.column()));
    }
}
