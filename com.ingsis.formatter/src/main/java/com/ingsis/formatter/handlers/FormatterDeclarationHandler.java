package com.ingsis.formatter.handlers;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public class FormatterDeclarationHandler implements NodeEventHandler<DeclarationKeywordNode> {
  private final Boolean hasPreAscriptionSpace;
  private final Boolean hasPostAscriptionSpace;
  private final Boolean isAssignationSpaced;
  private final NodeEventHandler<ExpressionNode> expressionHandler;
  private final ResultFactory resultFactory;

  public FormatterDeclarationHandler(Boolean hasPreAscriptionSpace, Boolean hasPostAscriptionSpace,
      Boolean isAssignationSpaced, NodeEventHandler<ExpressionNode> expressionHandler, ResultFactory resultFactory) {
    this.hasPreAscriptionSpace = hasPreAscriptionSpace;
    this.hasPostAscriptionSpace = hasPostAscriptionSpace;
    this.isAssignationSpaced = isAssignationSpaced;
    this.expressionHandler = expressionHandler;
    this.resultFactory = resultFactory;
  }

  @Override
  public Result<String> handle(DeclarationKeywordNode node) {
    StringBuilder sb = new StringBuilder();
    if (node.isMutable()) {
      sb.append("let ");
    } else {
      sb.append("const ");
    }
    sb.append(node.typeAssignationNode().identifierNode().name());
    if (hasPreAscriptionSpace) {
      sb.append(" ");
    }
    sb.append(":");
    if (hasPostAscriptionSpace) {
      sb.append(" ");
    }
    sb.append(node.typeAssignationNode().typeNode().type().name());
    if (isAssignationSpaced) {
      sb.append(" = ");
    } else {
      sb.append("=");
    }
    Result<String> formatExpressionResult = expressionHandler.handle(node.valueAssignationNode().expressionNode());
    if (!formatExpressionResult.isCorrect()) {
      return resultFactory.cloneIncorrectResult(formatExpressionResult);
    }
    sb.append(formatExpressionResult.result());
    sb.append(";");
    return resultFactory.createCorrectResult(sb.toString());
  }

}
