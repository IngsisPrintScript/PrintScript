package common.factories.nodes;

import common.nodes.Node;
import common.nodes.declaration.DeclarationNode;
import common.nodes.declaration.IdentifierNode;
import common.nodes.declaration.TypeNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.tokens.Token;
import common.tokens.TokenInterface;

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
    public Node createDeclarationNode() {
            return new DeclarationNode();
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
