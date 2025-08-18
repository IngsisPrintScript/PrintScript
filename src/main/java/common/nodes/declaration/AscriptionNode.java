package common.nodes.declaration;

import common.nodes.CompositeNode;
import common.nodes.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class AscriptionNode extends CompositeNode {
    public AscriptionNode(){
        super(2);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public Boolean hasType(){
        return this.children.get(0).isNil();
    }
    public Boolean hasIdentifier(){
        return this.children.get(1).isNil();
    }

    public Result type(){
        if (hasType()){
            return new CorrectResult<>(this.children.get(0));
        } else {
            return new IncorrectResult("Ascription node has no type.");
        }
    }
    public Result identifier(){
        if (hasIdentifier()){
            return new CorrectResult<>(this.children.get(1));
        } else {
            return new IncorrectResult("Ascription node has no identifier.");
        }
    }
    public Result setType(Node node){ return new CorrectResult<>(this.children.set(0, node)); }
    public Result setIdentifier(Node node){ return new CorrectResult<>(this.children.set(1, node)); }
}
