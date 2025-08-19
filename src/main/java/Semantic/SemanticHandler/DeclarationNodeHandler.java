package Semantic.SemanticHandler;

import Semantic.SemanticVisitor.SemanticVisitor;
import Semantic.Context.SemanticVisitorContext;
import common.Symbol.Symbol;
import common.nodes.Node;
import common.nodes.Declaration.DeclarationNode;
import common.nodes.Declaration.Identifier.IdentifierNode;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

public class DeclarationNodeHandler implements SemanticHandler<DeclarationNode>{
    @Override
    public Result handleSemantic(DeclarationNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        Object leftChild = ((CorrectResult<?>) node.leftChild()).newObject();
        Object rightChild = ((CorrectResult<?>) node.rightChild()).newObject();
        Result resolved = context.variablesTable().variablesTable().getValue(((IdentifierNode) rightChild).value());
        if(!resolved.isSuccessful()){
            return resolved;
        }
        Object result = ((CorrectResult<?>) resolved).newObject();
        if(!(result instanceof Symbol symbol)){
           return resolved;
        }
        if(!symbol.type().equals(((LiteralNode) leftChild).value())){
            return new IncorrectResult("Variable " + symbol.type() + " is already defined and mismatch Types.");
        }
        return new CorrectResult<>(node);
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof DeclarationNode;
    }
}
