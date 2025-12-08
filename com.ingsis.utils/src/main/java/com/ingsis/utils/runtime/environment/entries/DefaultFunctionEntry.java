/*
 * My Project
 */

package com.ingsis.utils.runtime.environment.entries;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressFBWarnings(
        value = "EI",
        justification = "Closure is a shared, controlled mutable dependency.")
public record DefaultFunctionEntry(
        Types returnType,
        LinkedHashMap<String, Types> arguments,
        List<ExpressionNode> body,
        Environment closure)
        implements FunctionEntry {

    public DefaultFunctionEntry {
        arguments = new LinkedHashMap<>(arguments);
        if (body == null) {
            body = null;
        } else {
            body = List.copyOf(body);
        }
    }
}
