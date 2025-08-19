package common.factories.nodes;

import common.nodes.Node;

public interface NodeFactoryInterface {
    Node createLetStatementNode();
    Node createPrintlnStatementNode();
    Node createIdentifierNode(String identifier);
    Node createAscriptionNode();
    Node createTypeNode(String type);
    Node createLiteralNode(String literal);
    Node createAdditionNode();
}
