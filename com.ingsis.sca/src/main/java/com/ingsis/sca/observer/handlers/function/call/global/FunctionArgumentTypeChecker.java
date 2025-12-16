/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.function.call.global;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.CallFunctionNode;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.List;

public class FunctionArgumentTypeChecker implements NodeEventHandler<Node> {
    private final String functionExpectedName;
    private final List<Class<? extends ExpressionNode>> allowedArgumentTypes;

    public FunctionArgumentTypeChecker(
            String functionExpectedName,
            List<Class<? extends ExpressionNode>> allowedArgumentTypes) {
        this.functionExpectedName = functionExpectedName;
        this.allowedArgumentTypes = allowedArgumentTypes;
    }

    @Override
    public CheckResult handle(Node node, SemanticEnvironment env) {
        if (!(node instanceof CallFunctionNode callFunctionNode)) {
            return new CheckResult.CORRECT(env);
        }

        String functionActualName = callFunctionNode.identifierNode().name();

        if (!functionActualName.equals(functionExpectedName)) {
            return new CheckResult.CORRECT(env);
        }

        List<ExpressionNode> arguments = callFunctionNode.argumentNodes();

        for (ExpressionNode argument : arguments) {
            if (!allowedArgumentTypes.contains(argument.getClass())) {
                return new CheckResult.INCORRECT(
                        env,
                        String.format(
                                "%s function does not accept argument number: %d type: %s"
                                        + "on line: %d and columnd: %d",
                                functionExpectedName,
                                arguments.indexOf(argument),
                                argument.getClass().toString(),
                                argument.line(),
                                argument.column()));
            }
        }
        return new CheckResult.CORRECT(env);
    }
}
