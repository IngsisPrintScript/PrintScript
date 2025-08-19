package Semantic.HandlersFact;

import Semantic.SemanticHandler.*;
import common.nodes.Declaration.DeclarationNode;
import common.nodes.Declaration.Identifier.IdentifierNode;
import common.nodes.Declaration.TypeNode.TypeNode;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;

import java.util.HashMap;
import java.util.Map;

public class HandlersFact {

    public Map<Class<?>, SemanticHandler<?>>  createMap() {
        Map<Class<?>, SemanticHandler<?>> handlers = new HashMap<>();

        handlers.put(AdditionExpressionNode.class, new AdditionExpressionNode());
        handlers.put(DeclarationNode.class, new DeclarationNodeHandler());
        handlers.put(LetStatementNode.class, new LetStatementNodeHandler());
        handlers.put(LiteralNode.class, new LiteralNodeHandler());
        handlers.put(TypeNode.class, new TypeNodeHandler());
        handlers.put(IdentifierNode.class, new IdentifierNodeHandler());

        return handlers;
    }

}
