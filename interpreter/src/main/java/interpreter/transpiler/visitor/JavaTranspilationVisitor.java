package interpreter.transpiler.visitor;

import nodes.common.NilNode;
import nodes.declaration.AscriptionNode;
import nodes.declaration.TypeNode;
import nodes.expression.ExpressionNode;
import nodes.expression.binary.AdditionNode;
import nodes.expression.binary.AssignationNode;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import nodes.statements.LetStatementNode;
import nodes.statements.PrintStatementNode;
import nodes.visitor.VisitorInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public class JavaTranspilationVisitor implements VisitorInterface {
  @Override
  public Result<String> visit(LetStatementNode node) {
    Result<AscriptionNode> getDeclarationResult = node.ascription();
    if (!getDeclarationResult.isSuccessful()) {
      return new IncorrectResult<>("The declaration of a let statement is incorrect.");
    }
    AscriptionNode declarationNode = getDeclarationResult.result();
    Result<String> visitDeclarationResult = declarationNode.accept(this);
    if (!visitDeclarationResult.isSuccessful()) return visitDeclarationResult;
    String declarationString = visitDeclarationResult.result();

    Result<ExpressionNode> getExpressionResult = node.expression();
    if (!getExpressionResult.isSuccessful()) {
      return new CorrectResult<>(declarationString + ";");
    }
    ExpressionNode expressionNode = getExpressionResult.result();
    Result<String> visitExpressionResult = expressionNode.accept(this);
    if (!visitExpressionResult.isSuccessful()) return visitExpressionResult;
    String expresionString = visitExpressionResult.result();

    return new CorrectResult<>(declarationString + " = " + expresionString + ";");
  }

  @Override
  public Result<String> visit(PrintStatementNode node) {
    Result<ExpressionNode> getExpressionResult = node.expression();
    if (!getExpressionResult.isSuccessful()) {
      return new IncorrectResult<>("The expression of a print statement is incorrect.");
    }
    ExpressionNode expressionNode = getExpressionResult.result();
    Result<String> visitExpressionResult = expressionNode.accept(this);
    if (!visitExpressionResult.isSuccessful()) return visitExpressionResult;
    String expresionString = visitExpressionResult.result();

    String resultString = "System.out.println(" + expresionString + ");";

    return new CorrectResult<>(resultString);
  }

  @Override
  public Result<String> visit(AscriptionNode node) {
    Result<TypeNode> getTypeResult = node.type();
    if (!getTypeResult.isSuccessful()) {
      return new IncorrectResult<>("The type of a ascription node is incorrect.");
    }
    TypeNode typeNode = getTypeResult.result();
    Result<String> visitTypeResult = typeNode.accept(this);
    if (!visitTypeResult.isSuccessful()) return visitTypeResult;
    String typeString = visitTypeResult.result();

    Result<IdentifierNode> getIdentifierResult = node.identifier();
    if (!getIdentifierResult.isSuccessful()) {
      return new IncorrectResult<>("The identifier of a ascription node is incorrect.");
    }
    IdentifierNode identifierNode = getIdentifierResult.result();
    Result<String> visitIdentifierResult = identifierNode.accept(this);
    if (!visitIdentifierResult.isSuccessful()) return visitIdentifierResult;
    String identifierString = visitIdentifierResult.result();

    return new CorrectResult<>(typeString + " " + identifierString);
  }

  @Override
  public Result<String> visit(AdditionNode node) {
    Result<ExpressionNode> getLeftChildResult = node.getLeftChild();
    if (!getLeftChildResult.isSuccessful()) {
      return new IncorrectResult<>("The left child of a addition node is incorrect.");
    }
    ExpressionNode leftChildNode = getLeftChildResult.result();
    Result<String> visitLeftChildResult = leftChildNode.accept(this);
    if (!visitLeftChildResult.isSuccessful()) return visitLeftChildResult;
    String leftChildString = visitLeftChildResult.result();

    Result<ExpressionNode> getRightChildResult = node.getRightChild();
    if (!getRightChildResult.isSuccessful()) {
      return new IncorrectResult<>("The right child of a addition node is incorrect.");
    }
    ExpressionNode rightChildNode = getRightChildResult.result();
    Result<String> visitRightChildResult = rightChildNode.accept(this);
    if (!visitRightChildResult.isSuccessful()) return visitRightChildResult;
    String rightChildString = visitRightChildResult.result();

    return new CorrectResult<>(leftChildString + " + " + rightChildString);
  }

  @Override
  public Result<String> visit(AssignationNode node) {
    Result<ExpressionNode> getIdentifierNodeResult = node.getLeftChild();
    if (!getIdentifierNodeResult.isSuccessful()) {
      return new IncorrectResult<>("The identifier of a assignation node is incorrect.");
    }
    IdentifierNode identifierNode = (IdentifierNode) getIdentifierNodeResult.result();

    Result<ExpressionNode> getRightChildResult = node.getRightChild();
    if (!getRightChildResult.isSuccessful()) {
      return new IncorrectResult<>("The right child of a assignation node is incorrect.");
    }
    ExpressionNode expressionNode = getRightChildResult.result();

    return new CorrectResult<>(
        identifierNode.name() + " = " + expressionNode.accept(this).result() + ";");
  }

  @Override
  public Result<String> visit(LiteralNode node) {
    return new CorrectResult<>(node.value());
  }

  @Override
  public Result<String> visit(IdentifierNode node) {
    return new CorrectResult<>(node.name());
  }

  @Override
  public Result<String> visit(TypeNode node) {
    return new CorrectResult<>(node.type());
  }

  @Override
  public Result<String> visit(NilNode node) {
    return new CorrectResult<>("");
  }
}
