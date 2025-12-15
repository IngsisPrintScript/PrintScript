/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.factories;

import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.parser.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.parser.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.AndInMemoryNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultHandlersFactory implements HandlerSupplier {
  @Override
  public NodeEventHandler<DeclarationKeywordNode> getDeclarationHandler() {
    NodeEventHandlerRegistry<DeclarationKeywordNode> handler = new AndInMemoryNodeEventHandlerRegistry<>();
    handler.register(new LetNodeEventVariableExistenceHandler());
    handler.register(new LetNodeCorrectTypeEventHandler());
    return handler;
  }

  @Override
  public NodeEventHandler<IfKeywordNode> getConditionalHandler() {
    NodeEventHandlerRegistry<IfKeywordNode> handlerRegistry = new AndInMemoryNodeEventHandlerRegistry<>();
    return handlerRegistry;
  }

  @Override
  public NodeEventHandler<ExpressionNode> getExpressionHandler() {
    NodeEventHandlerRegistry<ExpressionNode> handlerRegistry = new AndInMemoryNodeEventHandlerRegistry<>();
    handlerRegistry.register(
        new ExpressionNodeEventVariableExistenceHandler());
    handlerRegistry.register(new OperatorNodeValidityHandler());
    return handlerRegistry;
  }
}
