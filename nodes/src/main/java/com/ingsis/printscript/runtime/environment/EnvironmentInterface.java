/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.runtime.functions.PSFunction;

public interface EnvironmentInterface {
    Result<VariableEntry> putVariable(String identifier, VariableEntry entry);

    Result<VariableEntry> modifyVariableValue(String identifier, Object value);

    Result<String> getVariableType(String id);

    Result<Object> getVariableValue(String id);

    Boolean variableIsDeclared(String id);

    Result<PSFunction> getFunction(String identifier);

    Boolean hasFunction(String identifier);
}
