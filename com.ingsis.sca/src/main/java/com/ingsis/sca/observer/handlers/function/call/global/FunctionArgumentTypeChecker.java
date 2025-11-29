/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.function.call.global;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class FunctionArgumentTypeChecker implements NodeEventHandler<CallFunctionNode> {
    private final ResultFactory resultFactory;
    private final String functionExpectedName;
    private final Integer functionArgumentIndex;

    public FunctionArgumentTypeChecker(
            ResultFactory resultFactory,
            String functionExpectedName,
            Integer functionArgumentIndex) {
        this.resultFactory = resultFactory;
        this.functionExpectedName = functionExpectedName;
        this.functionArgumentIndex = functionArgumentIndex;
    }

    @Override
    public Result<String> handle(CallFunctionNode node) {
        String functionActualName = node.identifierNode().name();

        if (!functionActualName.equals(functionExpectedName)) {
            return resultFactory.createCorrectResult("Check passed.");
        }

        ExpressionNode expressionNode = node.argumentNodes().get(functionArgumentIndex);

        if ((expressionNode instanceof IdentifierNode) || (expressionNode instanceof LiteralNode)) {
            return resultFactory.createCorrectResult("Check passed.");
        } else {
            return resultFactory.createIncorrectResult(
                    String.format(
                            "%s function just accepts literals or variables for argument number: %d"
                                    + " and you provided: %s on line: %d and columnd: %d",
                            functionExpectedName,
                            functionArgumentIndex,
                            expressionNode.getClass().toString(),
                            expressionNode.line(),
                            expressionNode.column()));
        }
    }
}
