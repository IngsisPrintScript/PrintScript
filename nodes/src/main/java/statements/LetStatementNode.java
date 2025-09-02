package statements;


import common.NilNode;
import common.Node;
import declaration.AscriptionNode;
import expression.ExpressionNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;
import visitor.VisitorInterface;

import java.util.List;

public class LetStatementNode implements Node, SemanticallyCheckable {
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
}