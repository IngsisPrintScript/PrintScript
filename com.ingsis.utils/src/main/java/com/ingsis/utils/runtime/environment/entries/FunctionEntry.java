/*
 * My Project
 */

package com.ingsis.utils.runtime.environment.entries;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.type.types.Types;
import java.util.LinkedHashMap;
import java.util.List;

public interface FunctionEntry {

    Types returnType();

    LinkedHashMap<String, Types> arguments();

    List<ExpressionNode> body();

    Environment closure();
}
