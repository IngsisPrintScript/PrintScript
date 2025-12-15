/*
 * My Project
 */

package com.ingsis.utils.type.typer.literal;

import com.ingsis.utils.evalstate.env.Environment;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.expressions.BooleanLiteralNode;
import com.ingsis.utils.nodes.expressions.LiteralNode;
import com.ingsis.utils.nodes.expressions.NumberLiteralNode;
import com.ingsis.utils.nodes.expressions.StringLiteralNode;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.types.Types;

public final class DefaultLiteralTypeGetter implements TypeGetter<LiteralNode> {
  @Override
  public Types getType(LiteralNode expressionNode, Environment env) {
    return getType(expressionNode);
  }

  @Override
  public Types getType(LiteralNode expressionNode, SemanticEnvironment env) {
    return getType(expressionNode);
  }

  private Types getType(LiteralNode literalNode) {
    return switch (literalNode) {
      case StringLiteralNode ignored -> Types.STRING;
      case NumberLiteralNode ignored -> Types.NUMBER;
      case BooleanLiteralNode ignored -> Types.BOOLEAN;
    };
  }
}
