/*
 * My Project
 */

package com.ingsis.runtime.environment.entries.factories;

import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import java.util.Map;

public interface EntryFactory {
    FunctionEntry createFunctionEntry(
            Types returnType,
            Map<String, Types> arguments,
            List<ExpressionNode> body,
            Environment closure);

    VariableEntry createVariableEntry(Types type, Object value, Boolean isMutable);
}
