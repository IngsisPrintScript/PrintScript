package factories;


import common.Node;

public interface NodeFactoryInterface {
    Node createLetStatementNode();
    Node createPrintlnStatementNode();
    Node createIdentifierNode(String identifier);
    Node createAscriptionNode();
    Node createAssignationNode();
    Node createTypeNode(String type);
    Node createLiteralNode(String literal);
    Node createAdditionNode();
}
