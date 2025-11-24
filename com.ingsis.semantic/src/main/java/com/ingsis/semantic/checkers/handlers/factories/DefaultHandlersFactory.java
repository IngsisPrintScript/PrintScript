/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.FinalHandler;
import com.ingsis.rule.observer.handlers.InMemoryNodeEventHandlerRegistry;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultHandlersFactory implements HandlerFactory {
  private final Runtime runtime;
  private final ResultFactory resultFactory;

  public DefaultHandlersFactory(Runtime runtime, ResultFactory resultFactory) {
    this.runtime = runtime;
    this.resultFactory = resultFactory;
  }

  @Override
  public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
    NodeEventHandlerRegistry<DeclarationKeywordNode> handler = new InMemoryNodeEventHandlerRegistry<>(resultFactory);
    handler.register(new LetNodeEventVariableExistenceHandler(runtime, resultFactory));
    handler.register(new LetNodeCorrectTypeEventHandler(runtime, resultFactory));
    return handler;
  }

  @Override
  public NodeEventHandler<IfKeywordNode> createConditionalHandler() {
    NodeEventHandlerRegistry<IfKeywordNode> handlerRegistry = new InMemoryNodeEventHandlerRegistry<>(resultFactory);
    handlerRegistry.register(new FinalHandler<>(resultFactory));
    return handlerRegistry;
  }

  @Override
  public NodeEventHandler<ExpressionNode> createExpressionHandler() {
    NodeEventHandlerRegistry<ExpressionNode> handlerRegistry = new InMemoryNodeEventHandlerRegistry<>(resultFactory);
    handlerRegistry.register(new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory));
    handlerRegistry.register(new OperatorNodeValidityHandler(runtime, resultFactory));
    return handlerRegistry;
  }
}
