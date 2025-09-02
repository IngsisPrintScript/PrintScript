package nodes.factories;

import nodes.common.Node;
import nodes.declaration.AscriptionNode;
import nodes.declaration.TypeNode;
import nodes.expression.binary.AdditionNode;
import nodes.expression.binary.AssignationNode;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import nodes.statements.LetStatementNode;
import nodes.statements.PrintStatementNode;

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
