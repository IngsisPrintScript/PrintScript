/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression.identifier;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.environment.EnvironmentInterface;
import java.util.List;

public record IdentifierNode(String name) implements Node, ExpressionNode {

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> children() {
        return List.of();
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<Object> evaluate() {
        try {
            EnvironmentInterface currentEnv = Runtime.getInstance().currentEnv();
            Result<Object> getIdValue = currentEnv.getIdValue(this.name());
            if (!getIdValue.isSuccessful()) {
                return new IncorrectResult<>(getIdValue.errorMessage());
            }
            Object identifierValue = getIdValue.result();
            return new CorrectResult<>(identifierValue);
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
    }

    @Override
    public Result<String> prettyPrint() {
        return new CorrectResult<>(this.name());
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }
}
