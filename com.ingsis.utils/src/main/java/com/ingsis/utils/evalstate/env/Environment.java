/*
 * My Project
 */

package com.ingsis.utils.evalstate.env;

import java.util.Optional;

import com.ingsis.utils.evalstate.env.bindings.Binding;

public sealed interface Environment permits ScopedEnviroment {

  Environment define(String identifier, Binding binding);

  Optional<Binding> lookup(String identifier);

  Environment update(String identifier, Binding binding);

  Environment delete(String identifier);

  Environment enterScope();
}
