/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.type;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.typer.expression.DefaultExpressionTypeGetter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class IfNodeCorrectTypeEventHandler implements NodeEventHandler<IfKeywordNode> {
    @Override
    public CheckResult handle(IfKeywordNode node, SemanticEnvironment env) {
        Types expectedType = Types.BOOLEAN;
        Types actualType = new DefaultExpressionTypeGetter().getType(node.condition(), env);
        if (!expectedType.equals(actualType)) {
            return new CheckResult.INCORRECT(
                    env,
                    String.format(
                            "Condition did not have Boolean type on line:%d and column:%d",
                            node.line(), node.column()));
        }
        return new CheckResult.CORRECT(env);
    }
}
