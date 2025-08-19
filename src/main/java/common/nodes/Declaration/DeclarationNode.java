package common.nodes.Declaration;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class DeclarationNode extends CompositeNode {

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public Result leftChild(){
        if (hasLeftChild()){
            return new CorrectResult<>(this.children.getFirst());
        } else {
            return new IncorrectResult("Declaration has no left child.");
        }
    }

    public Result rightChild(){
        if (hasLeftChild()){
            return new CorrectResult<>(this.children.get(1));
        } else {
            return new IncorrectResult("Declaration has no right child.");
        }
    }
    public Result addLeftChild(Node node){
        return new CorrectResult<>(this.children.set(0, node));
    }
    public Result addRightChild(Node node){
        return new CorrectResult<>(this.children.set(1, node));
    }

    private Boolean hasLeftChild(){
        try {
            this.children.getFirst();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private Boolean hasRightChild(){
        try {
            this.children.get(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
