package parser.semantic.enforcers;

import common.Environment;
import nodes.declaration.AscriptionNode;
import nodes.declaration.TypeNode;
import nodes.expression.ExpressionNode;
import nodes.expression.binary.BinaryExpression;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import nodes.statements.LetStatementNode;
import nodes.statements.PrintStatementNode;
import parser.semantic.rules.SemanticRule;
import parser.semantic.rules.variables.DeclaredVariableSemanticRule;
import parser.semantic.rules.variables.NotDeclaredVariableSemanticRule;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public class VariablesExistenceRulesChecker extends parser.semantic.enforcers.SemanticRulesChecker {
  private final SemanticRule notDeclaredVariableSemanticRuleChecker =
      new NotDeclaredVariableSemanticRule();
  private final SemanticRule declaredVariableSemanticRuleChecker =
      new DeclaredVariableSemanticRule();
  private SemanticRule variableSemanticRuleChecker;

  @Override
  public Result<String> check(LetStatementNode node) {
    Result<AscriptionNode> getAscriptionResult = node.ascription();
    if (!getAscriptionResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    AscriptionNode ascription = getAscriptionResult.result();
    Result<IdentifierNode> getIdentifierNodeResult = ascription.identifier();
    if (!getIdentifierNodeResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    IdentifierNode identifier = getIdentifierNodeResult.result();
    variableSemanticRuleChecker = new NotDeclaredVariableSemanticRule();
    Result<String> checkIdIsNotDeclaredResult = this.check(identifier);
    if (!checkIdIsNotDeclaredResult.isSuccessful()) {
      return checkIdIsNotDeclaredResult;
    }
    ;
    Result<TypeNode> getTypeNodeResult = ascription.type();
    if (!getTypeNodeResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    TypeNode type = getTypeNodeResult.result();

    Environment.getInstance().putIdType(identifier.name(), type.type());

    Result<ExpressionNode> getExpressionResult = node.expression();
    if (!getExpressionResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    ExpressionNode expression = getExpressionResult.result();
    return expression.acceptCheck(this);
  }

  @Override
  public Result<String> check(PrintStatementNode node) {
    Result<ExpressionNode> getExpressionResult = node.expression();
    if (!getExpressionResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    ExpressionNode expression = getExpressionResult.result();
    return expression.acceptCheck(this);
  }

  @Override
  public Result<String> check(BinaryExpression node) {
    Result<ExpressionNode> getLeftChildResult = node.getLeftChild();
    if (!getLeftChildResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    ExpressionNode leftChild = getLeftChildResult.result();
    Result<String> checkRulesForLeftChild = leftChild.acceptCheck(this);
    if (!checkRulesForLeftChild.isSuccessful()) {
      return checkRulesForLeftChild;
    }

    Result<ExpressionNode> getRightChildResult = node.getRightChild();
    if (!getRightChildResult.isSuccessful()) {
      return new IncorrectResult<>("This tree does not pass this rule.");
    }
    ExpressionNode rightChild = getRightChildResult.result();
    Result<String> checkRulesForRightChild = rightChild.acceptCheck(this);
    if (!checkRulesForRightChild.isSuccessful()) {
      return checkRulesForRightChild;
    }

    return new CorrectResult<>("The node passes this check.");
  }

  @Override
  public Result<String> check(IdentifierNode node) {
    if (variableSemanticRuleChecker == null) {
      variableSemanticRuleChecker = new DeclaredVariableSemanticRule();
    }
    return variableSemanticRuleChecker.checkRules(node);
  }

  @Override
  public Result<String> check(LiteralNode node) {
    return new CorrectResult<>("The node passes this check.");
  }
}
