/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.operator.OperatorNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;
import java.util.Collection;
import java.util.Collections;

public record IfKeywordNode(
        OperatorNode condition,
        Collection<Interpretable> thenBody,
        Collection<Interpretable> elseBody)
        implements Node {

    public IfKeywordNode {
        thenBody = Collections.unmodifiableCollection(thenBody);
        elseBody = Collections.unmodifiableCollection(elseBody);
    }

    @Override
    public Collection<Interpretable> thenBody() {
        return Collections.unmodifiableCollection(thenBody);
    }

    @Override
    public Collection<Interpretable> elseBody() {
        return Collections.unmodifiableCollection(elseBody);
    }

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        return interpreter.interpret(this);
    }

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
