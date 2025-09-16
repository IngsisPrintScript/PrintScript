package com.ingsis.printscript.astnodes.statements;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.visitor.InterpretableNode;
import com.ingsis.printscript.visitor.RuleVisitor;
import com.ingsis.printscript.visitor.SemanticallyCheckable;
import com.ingsis.printscript.visitor.VisitorInterface;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IfStatementNode implements Node, SemanticallyCheckable, InterpretableNode {

    private final ExpressionNode condition;
    private final Collection<InterpretableNode> thenBody;
    private final Collection<InterpretableNode> elseBody;

    public IfStatementNode(ExpressionNode condition, Collection<InterpretableNode> thenBody, Collection<InterpretableNode> elseBody) {
        this.condition = condition;
        this.thenBody = Collections.unmodifiableCollection(thenBody);
        this.elseBody = Collections.unmodifiableCollection(elseBody);
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
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public ExpressionNode condition() {
        return condition;
    }

    public Collection<InterpretableNode> thenBody() {
        return Collections.unmodifiableCollection(thenBody);
    }
    public Collection<InterpretableNode> elseBody() {
        return Collections.unmodifiableCollection(elseBody);
    }

    @Override
    public Result<String> acceptInterpreter(InterpretVisitorInterface interpreter) {
        return interpreter.interpret(this);
    }
}
