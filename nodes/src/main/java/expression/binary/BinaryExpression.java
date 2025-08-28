package expression.binary;

import common.CompositeNode;
import common.Node;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import visitor.RuleVisitor;
import visitor.SemanticallyCheckable;

public abstract class BinaryExpression extends CompositeNode implements SemanticallyCheckable {
    public BinaryExpression(){
        super(2);
    }

    public Boolean hasLeftChild(){
        return !this.children.get(0).isNil();
    }
    public Boolean hasRightChild(){
        return !this.children.get(1).isNil();
    }

    public Result leftChild(){
        if (hasLeftChild()){
            return new CorrectResult<>(this.children.get(0));
        } else {
            return new IncorrectResult("Binary expression has no left child.");
        }
    }
    public Result rightChild(){
        if (hasRightChild()){
            return new CorrectResult<>(this.children.get(1));
        } else {
            return new IncorrectResult("Binary expression has no right child.");
        }
    }
    public Result addLeftChild(Node node){
        return new CorrectResult<>(this.children.set(0, node));
    }
    public Result addRightChild(Node node){
        return new CorrectResult<>(this.children.set(1, node));
    }

    @Override
    public Result acceptCheck(RuleVisitor checker){
        return checker.check(this);
    }
}
