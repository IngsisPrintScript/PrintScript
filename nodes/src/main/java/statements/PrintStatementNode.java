package statements;

import common.NilNode;
import common.Node;
import expression.ExpressionNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;
import visitor.VisitorInterface;

import java.util.List;

public class PrintStatementNode implements Node, SemanticallyCheckable {
    private Node expression;

    public PrintStatementNode() {
        this.expression = new NilNode();
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public Boolean hasExpression(){
        return !this.expression.isNil();
    }

    public Result<ExpressionNode> expression(){
        if(hasExpression()){
            return new CorrectResult<>((ExpressionNode) this.expression);
        }  else {
            return new IncorrectResult<>("Print statement has no expression.");
        }
    }
    public Result<ExpressionNode> setExpression(ExpressionNode expression){
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
}
