/*
 * My Project
 */

package com.ingsis.printscript.astnodes.statements;

import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.visitor.InterpretVisitorInterface;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.List;

public class PrintStatementNode implements Node, SemanticallyCheckable, InterpretableNode {
    private Node expression;

    public PrintStatementNode() {
        this.expression = new NilNode();
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public Boolean hasExpression() {
        return !this.expression.isNil();
    }

    public Result<ExpressionNode> expression() {
        if (hasExpression()) {
            return new CorrectResult<>((ExpressionNode) this.expression);
        } else {
            return new IncorrectResult<>("Print statement has no expression.");
        }
    }

    public Result<ExpressionNode> setExpression(ExpressionNode expression) {
        this.expression = expression;
        return new CorrectResult<>(expression);
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }

    @Override
    public List<Node> children() {
        return List.of(expression);
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<String> acceptInterpreter(InterpretVisitorInterface interpreter) {
        return interpreter.interpret(this);
    }
}
