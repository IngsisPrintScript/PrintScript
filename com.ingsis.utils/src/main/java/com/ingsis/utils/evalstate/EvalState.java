package com.ingsis.utils.evalstate;

import com.ingsis.utils.evalstate.env.Environment;

public sealed interface EvalState permits InMemoryEvalState {
  Environment env();

  EvalState withEnv(Environment newEnv);
}
