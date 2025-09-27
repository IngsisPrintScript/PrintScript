/*
 * My Project
 */

package com.ingsis.runtime.environment;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.entries.VariableEntry;

public sealed interface Environment permits GlobalEnvironment, DefaultEnvironment {
    Result<VariableEntry> putVariable(String identifier, VariableEntry variableEntry);

    Result<VariableEntry> modifyVariable(String identifier, VariableEntry variableEntry);

    Result<VariableEntry> getVariable(String identifier);

    void setExecutionResult(Result<String> result);

    Result<String> getExecutionResult();

    Boolean isVariableDeclared(String identifier);

    Boolean isVariableInitialized(String identifier);
}
