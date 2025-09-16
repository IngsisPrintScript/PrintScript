package com.ingsis.printscript.astnodes.statements;

import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.visitor.*;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;

import java.util.List;

public class IfStatementNode implements Node, SemanticallyCheckable, InterpretableNode {

    private ExpressionNode condition;
    private ExpressionNode thenExpr;
    private ExpressionNode elseExpr;

    public IfStatementNode() {
        this.condition = new NilNode();
        this.thenExpr = new NilNode();
        this.elseExpr = new NilNode();
    }

    @Override
    public List<Node> children() {
        return List.of(condition,thenExpr,elseExpr);
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

    @Override
    public Result<String> acceptInterpreter(InterpretVisitor interpreter) {
        return interpreter.interpret(this);
    }
    public Boolean hasCondition() {
        return !condition.isNil();
    }
    public Boolean hasThenExpr() {
        return !thenExpr.isNil();
    }
    public Boolean hasElseExpr() {
        return !elseExpr.isNil();
    }

    public Result<ExpressionNode> getCondition() {
        if (hasCondition()) {
            return new CorrectResult<>(condition);
        } else {
            return new IncorrectResult<>("It has no left child.");
        }
    }
    public Result<ExpressionNode> getThenExpr() {
        if (hasThenExpr()) {
            return new CorrectResult<>(thenExpr);
        }  else {
            return new IncorrectResult<>("It has no right child.");
        }
    }
    public Result<ExpressionNode> getElseExpr() {
        if (hasElseExpr()) {
            return new CorrectResult<>(elseExpr);
        }  else {
            return new IncorrectResult<>("It has no right child.");
        }
    }

    public Result<ExpressionNode> setCondition(ExpressionNode condition) {
        this.condition = condition;
        return new CorrectResult<>(condition);
    }
    public Result<ExpressionNode> setThenExpr(ExpressionNode thenExpr) {
        this.thenExpr = thenExpr;
        return new CorrectResult<>(thenExpr);
    }
    public Result<ExpressionNode> setElseExpr(ExpressionNode elseExpr) {
        this.elseExpr = elseExpr;
        return new CorrectResult<>(elseExpr);
    }


}
