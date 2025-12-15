package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.value.Value;

public sealed interface InterpretResult {

  public record CORRECT(EvalState evalState, Value value) implements InterpretResult {
  }

  public record INCORRECT(EvalState evalState, String error) implements InterpretResult {
  }

}
