/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.factories;

import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.parser.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.parser.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.rule.observer.handlers.AndNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultHandlersFactory implements HandlerSupplier {
  @Override
  public NodeEventHandler<Node> getDeclarationHandler() {
    NodeEventHandlerRegistry<Node> handler = new AndNodeEventHandlerRegistry<>();
    handler.register(new LetNodeEventVariableExistenceHandler());
    handler.register(new LetNodeCorrectTypeEventHandler());
    return handler;
  }

  @Override
  public NodeEventHandler<Node> getConditionalHandler() {
    NodeEventHandlerRegistry<Node> handlerRegistry = new AndNodeEventHandlerRegistry<>();
    return handlerRegistry;
  }

  @Override
  public NodeEventHandler<Node> getExpressionHandler() {
    NodeEventHandlerRegistry<Node> handlerRegistry = new AndNodeEventHandlerRegistry<>();
    handlerRegistry.register(
        new ExpressionNodeEventVariableExistenceHandler());
    handlerRegistry.register(new OperatorNodeValidityHandler());
    return handlerRegistry;
  }
}
