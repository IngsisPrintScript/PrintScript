package common.factories.nodes;

import common.nodes.Node;
import common.tokens.TokenInterface;

public interface NodeFactoryInterface {
    Node createLetStatementNode();
    Node createPrintlnStatementNode();
    Node createIdentifierNode(String identifier);
    Node createDeclarationNode();
    Node createTypeNode(String type);
    Node createLiteralNode(String literal);
}
