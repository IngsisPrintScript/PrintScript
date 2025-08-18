package common.nodes.statements;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class PrintStatementNode extends CompositeNode {
    public PrintStatementNode() {
        super(1);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
    public Boolean hasExpression(){
        return this.children.get(0).isNil();
    }
    public Result expression(){
        if(hasExpression()){
            return new CorrectResult<>(children.get(0));
        }  else {
            return new IncorrectResult("Print statement has no expression.");
        }
    }
    public Result addExpression(Node expression){
        this.children.set(0, expression);
        return new CorrectResult<>(expression);
    }
}
