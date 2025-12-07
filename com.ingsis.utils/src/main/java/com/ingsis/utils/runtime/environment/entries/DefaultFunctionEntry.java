/*
 * My Project
 */

package com.ingsis.utils.runtime.environment.entries;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings(
        value = "EI",
        justification = "Closure is a shared, controlled mutable dependency.")
public record DefaultFunctionEntry(
        Types returnType,
        Map<String, Types> arguments,
        List<ExpressionNode> body,
        Environment closure)
        implements FunctionEntry {

    public DefaultFunctionEntry {
        arguments = Map.copyOf(arguments);
        if (body == null) {
            body = null;
        } else {
            body = List.copyOf(body);
        }
    }
}
