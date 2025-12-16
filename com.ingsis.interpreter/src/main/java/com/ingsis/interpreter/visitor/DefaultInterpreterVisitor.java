/*
 * My Project
 */

package com.ingsis.interpreter.visitor;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.env.bindings.Binding;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.value.Value;
import com.ingsis.utils.value.Value.BooleanValue;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Optional;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultInterpreterVisitor implements Interpreter {
  @Override
  public InterpretResult interpret(IfKeywordNode ifKeywordNode, EvalState evalState) {
    InterpretResult interpretCondition = this.interpret(ifKeywordNode.condition(), evalState);
    return switch (interpretCondition) {
      case InterpretResult.INCORRECT I -> I;
      case InterpretResult.CORRECT C -> {
        switch (C.value()) {
          case BooleanValue B -> {
            if (B.v()) {
              yield interpretChildren(ifKeywordNode.thenBody(), evalState);
            } else {
              yield interpretChildren(ifKeywordNode.elseBody(), evalState);
            }

          }
          default -> {
            yield new InterpretResult.INCORRECT(evalState, "Unexpected type for condition,");
          }
        }
      }
    };
  }

  private InterpretResult interpretChildren(List<Node> children, EvalState evalState) {
    EvalState newEvalState = evalState;
    Value resultValue = Value.UnitValue.INSTANCE;
    for (Node child : children) {
      switch (child.acceptInterpreter(this, evalState)) {
        case InterpretResult.INCORRECT I:
          return I;
        case InterpretResult.CORRECT C:
          newEvalState = C.evalState();
          resultValue = C.value();
      }
    }
    return new InterpretResult.CORRECT(newEvalState, resultValue);
  }

  @Override
  public InterpretResult interpret(
      DeclarationKeywordNode declarationKeywordNode, EvalState evalState) {
    InterpretResult evaluateExpression = this.interpret(declarationKeywordNode.expressionNode(), evalState);
    Value value = null;
    switch (evaluateExpression) {
      case InterpretResult.INCORRECT I:
        return I;
      case InterpretResult.CORRECT C:
        value = C.value();
        break;
    }
    EvalState newEvalState = evalState.withEnv(
        evalState
            .env()
            .define(
                declarationKeywordNode.identifierNode().name(),
                new Binding.VariableBinding(
                    declarationKeywordNode.declaredType(),
                    declarationKeywordNode.isMutable(),
                    Optional.ofNullable(value))));
    return new InterpretResult.CORRECT(newEvalState, Value.UnitValue.INSTANCE);
  }

  @Override
  public InterpretResult interpret(ExpressionNode expressionNode, EvalState evalState) {
    return expressionNode.solve(evalState);
  }
}
