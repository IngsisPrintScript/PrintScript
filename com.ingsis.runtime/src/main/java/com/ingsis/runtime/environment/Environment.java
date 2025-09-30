/*
 * My Project
 */

package com.ingsis.runtime.environment;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;

public sealed interface Environment permits GlobalEnvironment, DefaultEnvironment {
    Result<VariableEntry> putVariable(String identifier, Types type);

    Result<VariableEntry> modifyVariable(String identifier, Object value);

    Result<VariableEntry> getVariable(String identifier);

    void setExecutionResult(Result<String> result);

    Result<String> getExecutionResult();

    Boolean isVariableDeclared(String identifier);

    Boolean isVariableInitialized(String identifier);
}
