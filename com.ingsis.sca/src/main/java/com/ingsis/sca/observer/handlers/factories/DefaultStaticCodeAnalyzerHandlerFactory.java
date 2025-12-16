/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.factories;

import com.ingsis.sca.observer.handlers.ConditionalSca;
import com.ingsis.sca.observer.handlers.DeclarationHandler;
import com.ingsis.sca.observer.handlers.function.call.global.FunctionArgumentTypeChecker;
import com.ingsis.sca.observer.handlers.identifier.IdentifierPatternChecker;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.expressions.LiteralNode;
import com.ingsis.utils.rule.observer.handlers.AndNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.OrNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultStaticCodeAnalyzerHandlerFactory implements HandlerSupplier {

  private final RuleStatusProvider ruleStatusProvider;

  public DefaultStaticCodeAnalyzerHandlerFactory(RuleStatusProvider ruleStatusProvider) {
    this.ruleStatusProvider = ruleStatusProvider;
  }

  @Override
  public NodeEventHandler<Node> getDeclarationHandler() {
    NodeEventHandler<Node> identifierChecker;
    String format = ruleStatusProvider.getRuleValue("identifier_format", String.class);
    if (format == null) {
      format = "default";
    }
    switch (format) {
      case "camel case" ->
        identifierChecker = new IdentifierPatternChecker(
            "^[a-z]+(?:[A-Z][a-z0-9]*)*$", "camelCase");
      case "snake case" ->
        identifierChecker = new IdentifierPatternChecker("^[a-z]+(?:_[a-z0-9]+)*$", "snake_case");
      default -> identifierChecker = new AndNodeEventHandlerRegistry<>();
    }
    return new DeclarationHandler(identifierChecker, getExpressionHandler());
  }

  @Override
  public NodeEventHandler<Node> getExpressionHandler() {
    NodeEventHandlerRegistry<Node> registry = new AndNodeEventHandlerRegistry<>();

    if (ruleStatusProvider.getRuleStatus("mandatory-variable-or-literal-in-println")) {
      registry = registry.register(new FunctionArgumentTypeChecker(
          "println", List.of(LiteralNode.class, IdentifierNode.class)));
    }
    if (ruleStatusProvider.getRuleStatus("mandatory-variable-or-literal-in-readInput")) {
      registry = registry.register(new FunctionArgumentTypeChecker(
          "readInput", List.of(LiteralNode.class, IdentifierNode.class)));
    }
    return registry;
  }

  @Override
  public NodeEventHandler<Node> getConditionalHandler() {
    AtomicReference<NodeEventHandler<Node>> registryRef = new AtomicReference<>();
    NodeEventHandlerRegistry<Node> registry = new OrNodeEventHandlerRegistry<>();
    registry = registry.register(getDeclarationHandler());
    registry = registry.register(getExpressionHandler());
    registry = registry.register(new ConditionalSca(registryRef::get));
    registryRef.set(registry);

    return registryRef.get();
  }
}
