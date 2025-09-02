package nodes.visitor;

import nodes.common.NilNode;
import nodes.declaration.AscriptionNode;
import nodes.declaration.TypeNode;
import nodes.expression.binary.AdditionNode;
import nodes.expression.binary.AssignationNode;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import nodes.statements.LetStatementNode;
import nodes.statements.PrintStatementNode;
import results.Result;

public interface VisitorInterface {
  Result<String> visit(LetStatementNode node);

  Result<String> visit(PrintStatementNode node);

  Result<String> visit(AscriptionNode node);

  Result<String> visit(AdditionNode node);

  Result<String> visit(AssignationNode node);

  Result<String> visit(LiteralNode node);

  Result<String> visit(IdentifierNode node);

  Result<String> visit(TypeNode node);

  Result<String> visit(NilNode node);
}
