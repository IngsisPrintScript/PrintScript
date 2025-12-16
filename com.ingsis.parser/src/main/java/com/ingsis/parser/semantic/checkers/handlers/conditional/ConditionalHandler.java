package com.ingsis.parser.semantic.checkers.handlers.conditional;

import java.util.List;
import java.util.function.Supplier;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class ConditionalHandler implements NodeEventHandler<Node> {
  private final Supplier<NodeEventHandler<Node>> nodeHandlerSupplier;

  public ConditionalHandler(Supplier<NodeEventHandler<Node>> nodeHandlerSupplier) {
    this.nodeHandlerSupplier = nodeHandlerSupplier;
  }

  @Override
  public CheckResult handle(Node node, SemanticEnvironment env) {
    if (!(node instanceof IfKeywordNode ifKeywordNode)) {
      return new CheckResult.CORRECT(env);
    }
    CheckResult thenChildrem = handleChildren(ifKeywordNode.thenBody(), env);
    return switch (thenChildrem) {
      case CheckResult.INCORRECT I -> I;
      case CheckResult.CORRECT C -> handleChildren(ifKeywordNode.elseBody(), C.environment());
    };
  }

  private CheckResult handleChildren(List<Node> children, SemanticEnvironment env) {
    SemanticEnvironment tempEnv = env;
    for (Node child : children) {
      switch (nodeHandlerSupplier.get().handle(child, env)) {
        case CheckResult.INCORRECT I:
          return I;
        case CheckResult.CORRECT C:
          tempEnv = C.environment();
          break;
      }
    }
    return new CheckResult.CORRECT(tempEnv);
  }
}
