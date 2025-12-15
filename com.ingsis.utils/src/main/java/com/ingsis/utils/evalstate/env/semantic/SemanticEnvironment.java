package com.ingsis.utils.evalstate.env.semantic;

import java.util.Optional;

import com.ingsis.utils.evalstate.env.semantic.bindings.SemanticBinding;

public sealed interface SemanticEnvironment permits ScopedSemanticEnvironment {

  SemanticEnvironment define(String identifier, SemanticBinding binding);

  Optional<SemanticBinding> lookup(String identifier);

  SemanticEnvironment enterScope();
}
