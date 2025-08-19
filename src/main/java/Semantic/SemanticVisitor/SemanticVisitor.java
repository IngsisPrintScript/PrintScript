package Semantic.SemanticVisitor;

import Semantic.SemanticHandler.SemanticHandler;
import Semantic.Context.SemanticVisitorContext;
import common.nodes.Declaration.TypeNode.TypeNode;
import common.nodes.Node;
import common.nodes.Declaration.DeclarationNode;
import common.nodes.Declaration.Identifier.IdentifierNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

import java.util.Map;

public record SemanticVisitor(SemanticVisitorContext context, Map<Class<?>, SemanticHandler<?>> handlerMap) implements VisitorInterface {

    @Override
    public Result visit(LetStatementNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(PrintStatementNode node) {
/*
        Result expr = node.expression();
        if (!expr.isSuccessful()) {
            return new IncorrectResult("Invalid expression in print statement.");
        }
        Object obj = ((CorrectResult<?>) expr).newObject();

        if (obj instanceof IdentifierNode id) {
            Result symbol = value.variablesTable().getValue(id.value());
            if (!symbol.isSuccessful()) {
                return new IncorrectResult("Variable " + id.value() + " is not defined.");
            }
            return new CorrectResult<>(((Symbol) ((CorrectResult<?>) symbol).newObject()).value());
        }
*/

        return dispatch(node);
    }

    @Override
    public Result visit(AdditionNode node){
        return dispatch(node);
    }

    @Override
    public Result visit(LiteralNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(IdentifierNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(DeclarationNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(TypeNode node) {
        return dispatch(node);
    }


    public <T> Result dispatch(T node){
        for(Map.Entry<Class<?>, SemanticHandler<?>> entry : handlerMap.entrySet()){
            SemanticHandler<?> handler = entry.getValue();
            if(handler.canHandle((Node) node)){
                return ((SemanticHandler<Node>) handler).handleSemantic((Node) node, context,this);
            }
        }
        return new IncorrectResult("No handler found for node: " + node.getClass().getSimpleName());
    }

}
