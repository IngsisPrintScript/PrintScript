/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression.function;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.function.argument.CallArgumentNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.functions.PSFunction;
import com.ingsis.printscript.visitor.RuleVisitor;
import com.ingsis.printscript.visitor.VisitorInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class CallFunctionNode implements ExpressionNode {
    private final IdentifierNode identifier;
    private final Collection<CallArgumentNode> arguments;

    public CallFunctionNode(IdentifierNode identifier, Collection<CallArgumentNode> arguments) {
        this.identifier = identifier;
        this.arguments = Collections.unmodifiableCollection(arguments);
    }

    public IdentifierNode identifier() {
        return identifier;
    }

    public Result<Collection<CallArgumentNode>> arguments() {
        return new CorrectResult<>(arguments);
    }

    @Override
    public Result<Object> evaluate() {
        Result<PSFunction> getFunctionResult = Runtime.getInstance().currentEnv().getFunction(identifier().name());
        if (!getFunctionResult.isSuccessful()) {
            return new IncorrectResult<>(getFunctionResult.errorMessage());
        }
        PSFunction function = getFunctionResult.result();
        try {
            return new CorrectResult<>(
                    function.call(arguments.stream().map(it -> (Object) it.value().value()).toList())
            );
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
    }

    @Override
    public Result<String> prettyPrint() {
        return new CorrectResult<>("");
    }

    @Override
    public List<Node> children() {
        Collection<Node> children = new ArrayList<>();
        children.add(identifier);
        children.addAll(arguments);
        return List.copyOf(children);
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
