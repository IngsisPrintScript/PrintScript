package parser.Semantic.HandlersFact;

import declaration.AscriptionNode;
import declaration.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import parser.Semantic.SemanticHandler.AdditionExpressionNodeHandler;
import parser.Semantic.SemanticHandler.DeclarationNodeHandler;
import parser.Semantic.SemanticHandler.IdentifierNodeHandler;
import parser.Semantic.SemanticHandler.LetStatementNodeHandler;
import parser.Semantic.SemanticHandler.LiteralNodeHandler;
import parser.Semantic.SemanticHandler.PrintStatementNodeHandler;
import parser.Semantic.SemanticHandler.SemanticHandler;
import parser.Semantic.SemanticHandler.TypeNodeHandler;
import statements.LetStatementNode;
import statements.PrintStatementNode;

import java.util.HashMap;
import java.util.Map;

public class HandlersFact {

    public Map<Class<?>, SemanticHandler<?>> createMap() {
        Map<Class<?>, SemanticHandler<?>> handlers = new HashMap<>();

        handlers.put(AdditionNode.class, new AdditionExpressionNodeHandler());
        handlers.put(AscriptionNode.class, new DeclarationNodeHandler());
        handlers.put(LetStatementNode.class, new LetStatementNodeHandler());
        handlers.put(LiteralNode.class, new LiteralNodeHandler());
        handlers.put(TypeNode.class, new TypeNodeHandler());
        handlers.put(IdentifierNode.class, new IdentifierNodeHandler());
        handlers.put(PrintStatementNode.class, new PrintStatementNodeHandler());

        return handlers;
    }

}
