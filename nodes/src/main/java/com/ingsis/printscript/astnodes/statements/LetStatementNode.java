package com.ingsis.printscript.astnodes.statements;


import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.astnodes.visitor.InterpretVisitor;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;

import java.util.List;

public class LetStatementNode implements Node, SemanticallyCheckable, InterpretableNode {
    private Node ascription;
    private Node expression;

    public LetStatementNode() {
        this.ascription = new NilNode();
        this.expression = new NilNode();
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public Boolean hasAscription(){
        return !this.ascription.isNil();
    }
    public Boolean hasExpression(){
        return !this.expression.isNil();
    }

    public Result<ExpressionNode> expression(){
        if(hasExpression()){
            return new CorrectResult<>((ExpressionNode) expression);
        }  else {
            return new IncorrectResult<>("Let statement has no expression.");
        }
    }
    public Result<AscriptionNode> ascription(){
        if(hasAscription()){
            return new CorrectResult<>((AscriptionNode) ascription);
        } else {
            return new IncorrectResult<>("Let statement has no declaration.");
        }
    }

    public Result<AscriptionNode> setAscription(AscriptionNode declaration){
        this.ascription = declaration;
        return new CorrectResult<>(declaration);
    }
    public Result<ExpressionNode> setExpression(ExpressionNode expression){
        this.expression = expression;
        return new CorrectResult<>(expression);
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return  checker.check(this);
    }

    @Override
    public List<Node> children() {
        return List.of();
    }

    @Override
    public Boolean isNil() {
        return null;
    }

    @Override
    public Result<String> acceptInterpreter(InterpretVisitor interpreter) {
        return interpreter.interpret(this);
    }
}