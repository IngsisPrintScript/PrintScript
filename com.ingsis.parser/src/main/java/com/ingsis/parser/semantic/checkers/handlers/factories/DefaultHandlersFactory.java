/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.factories;

import java.util.concurrent.atomic.AtomicReference;

import com.ingsis.parser.semantic.checkers.handlers.conditional.ConditionalHandler;
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
    NodeEventHandlerRegistry<Node> registry = new AndNodeEventHandlerRegistry<>();
    registry = registry.register(new LetNodeEventVariableExistenceHandler());
    registry = registry.register(new LetNodeCorrectTypeEventHandler());
    return registry;
  }

  @Override
  public NodeEventHandler<Node> getConditionalHandler() {
    AtomicReference<NodeEventHandler<Node>> registryRef = new AtomicReference<>();
    NodeEventHandlerRegistry<Node> registry = new AndNodeEventHandlerRegistry<>();
    registry = registry.register(getDeclarationHandler());
    registry = registry.register(getExpressionHandler());
    registry = registry.register(new ConditionalHandler(registryRef::get));
    registryRef.set(registry);

    return registryRef.get();
  }

  @Override
  public NodeEventHandler<Node> getExpressionHandler() {
    NodeEventHandlerRegistry<Node> registry = new AndNodeEventHandlerRegistry<>();
    registry = registry.register(new ExpressionNodeEventVariableExistenceHandler());
    registry = registry.register(new OperatorNodeValidityHandler());
    return registry;
  }
}
