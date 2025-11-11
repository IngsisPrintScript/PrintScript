/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeEventVariableExistenceHandler
    implements NodeEventHandler<DeclarationKeywordNode> {
  private final Runtime runtime;
  private final ResultFactory resultFactory;

  public LetNodeEventVariableExistenceHandler(Runtime runtime, ResultFactory resultFactory) {
    this.runtime = runtime;
    this.resultFactory = resultFactory;
  }

  @Override
  public Result<String> handle(DeclarationKeywordNode node) {
    TypeAssignationNode typeAssignationNode = node.typeAssignationNode();

    Result<String> checkTypeAssignationNode = new TypeAssignationNodeEventVariableExistenceHandler(runtime,
        resultFactory)
        .handle(typeAssignationNode);

    if (!checkTypeAssignationNode.isCorrect()) {
      return resultFactory.cloneIncorrectResult(checkTypeAssignationNode);
    }

    ValueAssignationNode valueAssignationNode = node.valueAssignationNode();

    Result<String> checkValueAssignationNode = new ValueAssignationNodeEventVariableExistenceHandler(runtime,
        resultFactory)
        .handle(valueAssignationNode);

    if (!checkValueAssignationNode.isCorrect()) {
      return resultFactory.cloneIncorrectResult(checkValueAssignationNode);
    }
    return resultFactory.createCorrectResult("Check passed.");
  }
}
