package common.nodes.statements;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class LetStatementNode extends CompositeNode {
    public LetStatementNode() {
        super();
    }
    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
    public Boolean hasDeclaration(){
        try {
            this.children.get(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Result declaration(){
        if(hasDeclaration()){
            return new CorrectResult<>(children.get(0));
        } else {
            return new IncorrectResult("Let statement has no declaration.");
        }
    }
    public Result addDeclaration(Node expression){
        try {
            this.children.set(0, expression);
            return new CorrectResult<>(expression);
        } catch (ClassCastException e) {
            return new IncorrectResult(e.getMessage());
        }
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
            return new IncorrectResult("Let statement has no expression.");
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
