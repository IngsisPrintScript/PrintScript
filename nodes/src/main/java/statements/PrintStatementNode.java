package statements;

import common.CompositeNode;
import common.Node;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;
import visitor.VisitorInterface;

public class PrintStatementNode extends CompositeNode implements SemanticallyCheckable {
    public PrintStatementNode() {
        super(1);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
    public Boolean hasExpression(){
        return !this.children.get(0).isNil();
    }
    public Result expression(){
        if(hasExpression()){
            return new CorrectResult<>(children.get(0));
        }  else {
            return new IncorrectResult("Print statement has no expression.");
        }
    }
    public Result setExpression(Node expression){
        this.children.set(0, expression);
        return new CorrectResult<>(expression);
    }

    @Override
    public Result acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }
}
