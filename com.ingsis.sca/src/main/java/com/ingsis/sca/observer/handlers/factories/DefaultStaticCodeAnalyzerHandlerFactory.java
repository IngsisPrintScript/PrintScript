/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.factories;

import com.ingsis.sca.observer.handlers.DeclarationHandler;
import com.ingsis.sca.observer.handlers.FinalHandler;
import com.ingsis.sca.observer.handlers.identifier.IdentifierPatternChecker;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.InMemoryNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;

public class DefaultStaticCodeAnalyzerHandlerFactory implements HandlerFactory {
  private final ResultFactory resultFactory;
  private final RuleStatusProvider ruleStatusProvider;

  public DefaultStaticCodeAnalyzerHandlerFactory(ResultFactory resultFactory, RuleStatusProvider ruleStatusProvider) {
    this.resultFactory = resultFactory;
    this.ruleStatusProvider = ruleStatusProvider;
  }

  @Override
  public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
    NodeEventHandlerRegistry<IdentifierNode> declarationHandler = new InMemoryNodeEventHandlerRegistry<>(resultFactory);
    String format = ruleStatusProvider.getRuleValue("identifier_format", String.class);
    if ("camel case".equals(format)) {
      declarationHandler.register(
          new IdentifierPatternChecker(
              resultFactory,
              "^[a-z]+(?:[A-Z][a-z0-9]*)*$",
              "camelCase"));
    } else if ("snake case".equals(format)) {
      declarationHandler.register(
          new IdentifierPatternChecker(
              resultFactory,
              "^[a-z]+(?:_[a-z0-9]+)*$",
              "snake_case"));
    }
    return new DeclarationHandler(declarationHandler, this.createExpressionHandler());
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
