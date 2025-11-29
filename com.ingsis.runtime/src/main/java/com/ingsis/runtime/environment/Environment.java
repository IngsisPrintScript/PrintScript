/*
 * My Project
 */

package com.ingsis.runtime.environment;

import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import java.util.Map;

public sealed interface Environment permits GlobalEnvironment, DefaultEnvironment {
    Result<VariableEntry> createVariable(String identifier, Types type, Object value);

    Result<VariableEntry> createVariable(String identifier, Types type);

    Map<String, VariableEntry> readAll();

    Result<VariableEntry> readVariable(String identifier);

    Result<VariableEntry> updateVariable(String identifier, Object value);

    Result<VariableEntry> deleteVariable(String identifier);

    Boolean isVariableDeclared(String identifier);

    Boolean isVariableInitialized(String identifier);

    Result<FunctionEntry> createFunction(
            String identifier, Map<String, Types> arguments, Types returnType);

    Result<FunctionEntry> readFunction(String identifier);

    Result<FunctionEntry> updateFunction(String identifier, List<ExpressionNode> body);

    Boolean isFunctionDeclared(String identifier);

    Boolean isFunctionInitialized(String identifier);

    Boolean isIdentifierDeclared(String identifier);

    Boolean isIdentifierInitialized(String identifier);
}
