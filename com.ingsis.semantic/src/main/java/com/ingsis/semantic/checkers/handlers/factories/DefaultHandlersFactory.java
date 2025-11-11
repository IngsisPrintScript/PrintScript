/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.identifier.existance.TypeAssignationNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.identifier.existance.ValueAssignationNodeEventVariableExistenceHandler;
import com.ingsis.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultHandlersFactory implements HandlersFactory {
  private final Runtime runtime;
  private final ResultFactory resultFactory;

  public DefaultHandlersFactory(Runtime runtime, ResultFactory resultFactory) {
    this.runtime = runtime;
    this.resultFactory = resultFactory;
  }

  @Override
  public NodeEventHandler<DeclarationKeywordNode> createLetVariableExistenceHandler() {
    return new LetNodeEventVariableExistenceHandler(runtime, resultFactory);
  }

  @Override
  public NodeEventHandler<TypeAssignationNode> createTypeAssignationVariableExistenceHandler() {
    return new TypeAssignationNodeEventVariableExistenceHandler(runtime, resultFactory);
  }

  @Override
  public NodeEventHandler<ValueAssignationNode> createValueAssignationVariableExistenceHandler() {
    return new ValueAssignationNodeEventVariableExistenceHandler(runtime, resultFactory);
  }

  @Override
  public NodeEventHandler<ExpressionNode> createExpressionVariableExistenceHandler() {
    return new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory);
  }

  @Override
  public NodeEventHandler<ExpressionNode> createOperatorValidityHandler() {
    return new OperatorNodeValidityHandler(runtime, resultFactory);
  }

  @Override
  public NodeEventHandler<DeclarationKeywordNode> createLetCorrectTypeHandler() {
    return new LetNodeCorrectTypeEventHandler(runtime, resultFactory);
  }
}
