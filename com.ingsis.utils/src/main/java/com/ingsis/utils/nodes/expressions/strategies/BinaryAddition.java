/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.value.Value;

import java.util.List;

public class BinaryAddition implements ExpressionStrategy {

  @Override
  public InterpretResult solve(List<ExpressionNode> arguments, EvalState evalState) {
    Value left = Value.UnitValue.INSTANCE;
    InterpretResult leftRes = arguments.get(0).solve(evalState);
    switch (leftRes) {
      case InterpretResult.CORRECT C -> {
        evalState = C.evalState();
        left = C.value();
      }
      case InterpretResult.INCORRECT I -> {
        return I;
      }
    }
    Value right = Value.UnitValue.INSTANCE;
    InterpretResult rightRes = arguments.get(1).solve(evalState);
    switch (rightRes) {
      case InterpretResult.CORRECT C -> {
        evalState = C.evalState();
        right = C.value();
      }
      case InterpretResult.INCORRECT I -> {
        return I;
      }
    }

    return switch (left) {
      case Value.IntValue LI ->
        switch (right) {
          case Value.IntValue RI ->
            new InterpretResult.CORRECT(
                evalState, new Value.IntValue(LI.v().add(RI.v())));
          default ->
            new InterpretResult.INCORRECT(evalState, "Adding incorrect types.");
        };
      case Value.BooleanValue LB ->
        switch (right) {
          case Value.BooleanValue RB ->
            new InterpretResult.CORRECT(
                evalState, new Value.BooleanValue(LB.v() && RB.v()));
          default ->
            new InterpretResult.INCORRECT(evalState, "Adding incorrect types.");
        };
      case Value.StringValue LS ->
        switch (right) {
          case Value.StringValue RS ->
            new InterpretResult.CORRECT(
                evalState, new Value.StringValue(LS.v() + RS.v()));
          case Value.IntValue RV ->
            new InterpretResult.CORRECT(
                evalState, new Value.StringValue(LS.v() + RV.v().toString()));
          case Value.BooleanValue RB ->
            new InterpretResult.CORRECT(
                evalState, new Value.StringValue(LS.v() + Boolean.toString(RB.v())));

          default ->
            new InterpretResult.INCORRECT(evalState, "Adding incorrect types.");
        };
      default -> new InterpretResult.INCORRECT(evalState, "Adding incorrect types.");
    };
  }
}
