/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.identifier;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class IdentifierPatternChecker implements NodeEventHandler<Node> {
  private final String regEx;
  private final String patternName;

  public IdentifierPatternChecker(String regEx, String patternName) {
    this.regEx = regEx;
    this.patternName = patternName;
  }

  @Override
  public CheckResult handle(Node node, SemanticEnvironment env) {
    if (!(node instanceof IdentifierNode identifierNode)) {
      return new CheckResult.CORRECT(env);
    }
    String identifierName = identifierNode.name();
    if (identifierName.matches(regEx)) {
      return new CheckResult.CORRECT(env);
    } else {
      return new CheckResult.INCORRECT(
          env,
          String.format(
              "Identifier: %s did not respected %s on line: %d and column %d",
              patternName, identifierName, node.line(), node.column()));
    }
  }
}
