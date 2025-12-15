package com.ingsis.utils.evalstate.env;

import java.util.Map;
import java.util.Optional;

import com.ingsis.utils.evalstate.env.bindings.Binding;

import java.util.HashMap;

public record ScopedEnviroment(
    Environment parent,
    Map<String, Binding> bindings) implements Environment {

  public ScopedEnviroment {
    bindings = Map.copyOf(bindings);
  }

  @Override
  public Environment define(String identifier, Binding binding) {
    Map<String, Binding> newBindings = new HashMap<>(bindings);
    newBindings.put(identifier, binding);
    return new ScopedEnviroment(parent, Map.copyOf(newBindings));
  }

  @Override
  public Optional<Binding> lookup(String identifier) {
    Binding local = bindings.get(identifier);
    if (local != null) {
      return Optional.of(local);
    }
    return parent != null
        ? parent.lookup(identifier)
        : Optional.empty();
  }

  @Override
  public Environment update(String identifier, Binding binding) {
    if (bindings.containsKey(identifier)) {
      Map<String, Binding> newBindings = new HashMap<>(bindings);
      newBindings.put(identifier, binding);
      return new ScopedEnviroment(parent, Map.copyOf(newBindings));
    }

    if (parent != null) {
      Environment newParent = parent.update(identifier, binding);
      return new ScopedEnviroment(newParent, bindings);
    }

    return this;
  }

  @Override
  public Environment delete(String identifier) {
    if (bindings.containsKey(identifier)) {
      Map<String, Binding> newBindings = new HashMap<>(bindings);
      newBindings.remove(identifier);
      return new ScopedEnviroment(parent, Map.copyOf(newBindings));
    }

    return this;
  }

  @Override
  public Environment enterScope() {
    return new ScopedEnviroment(this, Map.of());
  }
}
