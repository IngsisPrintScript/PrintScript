/*
 * My Project
 */

package com.ingsis.utils.rule.observer;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;

public final class EventsChecker implements Checker {
  private final HandlerSupplier handlerSupplier;

  public EventsChecker(HandlerSupplier handlerSupplier) {
    this.handlerSupplier = handlerSupplier;
  }

  @Override
  public CheckResult check(IfKeywordNode ifKeywordNode, SemanticEnvironment env) {
    return handlerSupplier.getConditionalHandler().handle(ifKeywordNode, env);
  }

  @Override
  public CheckResult check(DeclarationKeywordNode letKeywordNode, SemanticEnvironment env) {
    return handlerSupplier.getDeclarationHandler().handle(letKeywordNode, env);
  }

  @Override
  public CheckResult check(ExpressionNode expressionNode, SemanticEnvironment env) {
    return handlerSupplier.getExpressionHandler().handle(expressionNode, env);
  }
}
