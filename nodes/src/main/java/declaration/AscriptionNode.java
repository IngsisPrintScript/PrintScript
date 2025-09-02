package declaration;

import common.NilNode;
import common.Node;
import expression.identifier.IdentifierNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import visitor.VisitorInterface;

import java.util.List;

public class AscriptionNode implements Node {
    private Node type;
    private Node identifier;

    public AscriptionNode(){
        this.type = new NilNode();
        this.identifier = new NilNode();
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    public Boolean hasType(){
        return !this.type.isNil();
    }
    public Boolean hasIdentifier(){
        return !this.identifier.isNil();
    }

    public Result<TypeNode> type(){
        if (hasType()){
            return new CorrectResult<>((TypeNode) this.type);
        } else {
            return new IncorrectResult<>("Ascription node has no type.");
        }
    }
    public Result<IdentifierNode> identifier(){
        if (hasIdentifier()){
            return new CorrectResult<>((IdentifierNode) this.identifier);
        } else {
            return new IncorrectResult<>("Ascription node has no identifier.");
        }
    }
    public Result<TypeNode> setType(TypeNode node){
        this.type = node;
        return new CorrectResult<>(node);
    }
    public Result<IdentifierNode> setIdentifier(IdentifierNode node){
        this.identifier = node;
        return new CorrectResult<>(node);
    }

    @Override
    public List<Node> children() {
        return List.of(type, identifier);
    }

    @Override
    public Boolean isNil() {
        return false;
    }
}
