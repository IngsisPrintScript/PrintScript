package common.nodes.statements;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class PrintStatementNode extends CompositeNode {
    @Override
    public Result accept(VisitorInterface visitor) {
        Result visitResult = visitor.visit(this);

        if (!visitor.managesVisitFlow()) {
            for (Node child : children) {
                Result childResult = child.accept(visitor);
                if (!childResult.isSuccessful()){
                    return childResult;
                }
            }
        }

        return visitResult;
    }
    public Boolean hasExpression(){
        try {
            this.children.get(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Result expression(){
        if(hasExpression()){
            return new CorrectResult<>(children.get(1));
        }  else {
            return new IncorrectResult("Print statement has no expression.");
        }
    }
    public Result addExpression(Node expression){
        try {
            this.children.set(1, expression);
            return new CorrectResult<>(expression);
        } catch (ClassCastException e) {
            return new IncorrectResult(e.getMessage());
        }
    }
}
