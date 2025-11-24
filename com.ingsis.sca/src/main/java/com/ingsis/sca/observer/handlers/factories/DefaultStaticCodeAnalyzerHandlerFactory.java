/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.sca.observer.handlers.DeclarationHandler;
import com.ingsis.sca.observer.handlers.FinalHandler;
import com.ingsis.sca.observer.handlers.identifier.IdentifierPatternChecker;

public class DefaultStaticCodeAnalyzerHandlerFactory implements HandlerFactory {
  private final ResultFactory resultFactory;

  public DefaultStaticCodeAnalyzerHandlerFactory(ResultFactory resultFactory) {
    this.resultFactory = resultFactory;
  }

  @Override
  public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
    NodeEventHandler<IdentifierNode> patternChecker = new IdentifierPatternChecker(
        resultFactory, "^[a-z]+(?:[A-Z][a-z0-9]*)*$", "camelCase");
    return new DeclarationHandler(patternChecker, this.createExpressionHandler());
  }

  @Override
  public NodeEventHandler<IfKeywordNode> createConditionalHandler() {
    return new FinalHandler<>(resultFactory);
  }

  @Override
  public NodeEventHandler<ExpressionNode> createExpressionHandler() {
    return new FinalHandler<>(resultFactory);
  }
}
