package parser.Semantic.SemanticVisitor;

import common.NilNode;
import common.Node;
import responses.IncorrectResult;
import responses.Result;
import declaration.AscriptionNode;
import declaration.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticHandler.SemanticHandler;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.VisitorInterface;

import java.util.Map;

public record SemanticVisitor(Map<Class<?>, SemanticHandler<?>> handlerMap) implements VisitorInterface {

    @Override
    public Result visit(LetStatementNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(PrintStatementNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(AdditionNode node) {
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
    public Result visit(AscriptionNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(TypeNode node) {
        return dispatch(node);
    }

    @Override
    public Result visit(NilNode node) {
        return dispatch(node);
    }


    public <T> Result dispatch(T node) {
        for (Map.Entry<Class<?>, SemanticHandler<?>> entry : handlerMap.entrySet()) {
            SemanticHandler<?> handler = entry.getValue();
            if (handler.canHandle((Node) node)) {
                return ((SemanticHandler<Node>) handler).handleSemantic((Node) node,this);
            }
        }
        return new IncorrectResult("No handler found for node: " + node.getClass().getSimpleName());
    }

}
