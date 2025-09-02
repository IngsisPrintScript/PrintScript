package factories;


import common.Node;
import declaration.AscriptionNode;
import expression.binary.AssignationNode;
import expression.identifier.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public class NodeFactory implements NodeFactoryInterface {
    @Override
    public Node createLetStatementNode() {
        return new LetStatementNode();
    }

    @Override
    public Node createPrintlnStatementNode() {
        return new PrintStatementNode();
    }

    @Override
    public Node createIdentifierNode(String identifier) {
        return new IdentifierNode(identifier);
    }

    @Override
    public Node createAscriptionNode() {
            return new AscriptionNode();
    }

    public Node createAssignationNode() {
            return new AssignationNode();
    }

    @Override
    public Node createTypeNode(String type) {
        return new TypeNode(type);
    }

    @Override
    public Node createLiteralNode(String literal) {
        return new LiteralNode(literal);
    }

    @Override
    public Node createAdditionNode() {
        return new AdditionNode();
    }
}
