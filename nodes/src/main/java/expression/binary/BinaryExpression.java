package common.nodes.expression.binary;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

public abstract class BinaryExpression extends CompositeNode {
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
}
