package com.ingsis.utils.evalstate.io;

import com.ingsis.utils.value.Value;

@FunctionalInterface
public interface OutputEmitter {
  void emit(Value value);
}
