/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import com.ingsis.runtime.environment.Environment;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
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
