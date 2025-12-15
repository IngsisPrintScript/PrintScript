package com.ingsis.utils.evalstate.factories;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.InMemoryEvalState;
import com.ingsis.utils.evalstate.env.factories.EnvironmentFactory;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;

public class DefaultEvalStateFactory implements EvalStateFactory {
  private final EnvironmentFactory envFactory;

  public DefaultEvalStateFactory(EnvironmentFactory envFactory) {
    this.envFactory = envFactory;
  }

  @Override
  public EvalState create(OutputEmitter emitter, InputSupplier supplier) {
    return new InMemoryEvalState(envFactory.createRoot(emitter, supplier));
  }

}
