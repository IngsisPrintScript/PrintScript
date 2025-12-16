package com.ingsis.utils.evalstate.env.semantic.factories;

import com.ingsis.utils.evalstate.env.semantic.ScopedSemanticEnvironment;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.evalstate.env.semantic.bindings.SemanticBinding;
import com.ingsis.utils.type.types.Types;

import java.util.Map;

public class DefaultSemanticEnvironmentFactory implements SemanticEnvironmentFactory {
  @Override
  public SemanticEnvironment root() {
    SemanticEnvironment env = new ScopedSemanticEnvironment(null, Map.of());
    env = env.define(
        "println",
        new SemanticBinding.FunctionBinding(Types.NIL));
    env = env.define(
        "readEnv",
        new SemanticBinding.FunctionBinding(Types.STRING));
    env = env.define(
        "readInput",
        new SemanticBinding.FunctionBinding(Types.UNDEFINED));
    return env;
  }
}
