package common.nodes.expression.binary;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

public abstract class BinaryExpression extends CompositeNode {
    public Boolean hasLeftChild(){
        try {
            this.children.getFirst();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Boolean hasRightChild(){
        try {
            this.children.get(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Result leftChild(){
        if (hasLeftChild()){
            return new CorrectResult<>(this.children.getFirst());
        } else {
            return new IncorrectResult("Binary expression has no left child.");
        }
    }
    public Result rightChild(){
        if (hasLeftChild()){
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
