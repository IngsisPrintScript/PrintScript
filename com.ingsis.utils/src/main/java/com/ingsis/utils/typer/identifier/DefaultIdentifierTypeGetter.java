/*
 * My Project
 */

package com.ingsis.utils.typer.identifier;

import java.util.Optional;

import com.ingsis.utils.evalstate.env.Environment;
import com.ingsis.utils.evalstate.env.bindings.Binding;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.evalstate.env.semantic.bindings.SemanticBinding;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultIdentifierTypeGetter implements TypeGetter<IdentifierNode> {
  @Override
  public Types getType(IdentifierNode expressionNode, Environment env) {
    String functionIdentifier = expressionNode.name();
    Optional<Binding> ob = env.lookup(functionIdentifier);
    if (ob.isPresent()) {
      return ob.get().type();
    }
    return Types.NIL;
  }

  @Override
  public Types getType(IdentifierNode expressionNode, SemanticEnvironment env) {
    String functionIdentifier = expressionNode.name();
    Optional<SemanticBinding> ob = env.lookup(functionIdentifier);
    if (ob.isPresent()) {
      return ob.get().type();
    }
    return Types.NIL;
  }
}
