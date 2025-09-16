/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.VariableEntry;

public class NilEnvironment implements EnvironmentInterface {

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry entry) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Result<VariableEntry> modifyVariableValue(String identifier, Object value) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Result<String> getVariableType(String id) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Result<Object> getVariableValue(String id) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return false;
    }
}
