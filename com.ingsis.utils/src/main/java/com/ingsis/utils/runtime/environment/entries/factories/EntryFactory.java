/*
 * My Project
 */

package com.ingsis.utils.runtime.environment.entries.factories;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.type.types.Types;
import java.util.LinkedHashMap;
import java.util.List;

public interface EntryFactory {
    FunctionEntry createFunctionEntry(
            Types returnType,
            LinkedHashMap<String, Types> arguments,
            List<ExpressionNode> body,
            Environment closure);

    VariableEntry createVariableEntry(Types type, Object value, Boolean isMutable);
}
