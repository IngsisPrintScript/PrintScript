/*
 * My Project
 */

package com.ingsis.utils.type.typer.literal;

import com.ingsis.utils.nodes.expressions.atomic.literal.BooleanLiteralNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.LiteralNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.NumberLiteralNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.StringLiteralNode;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.types.Types;

public final class DefaultLiteralTypeGetter implements TypeGetter<LiteralNode> {
  @Override
  public Types getType(LiteralNode expressionNode) {
    return switch (expressionNode) {
      case StringLiteralNode ignored -> Types.STRING;
      case NumberLiteralNode ignored -> Types.NUMBER;
      case BooleanLiteralNode ignored -> Types.BOOLEAN;
    };
  }
}
