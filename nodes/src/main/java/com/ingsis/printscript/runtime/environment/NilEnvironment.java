/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.FunctionEntry;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.visitor.InterpretableNode;
import java.util.Collection;

public class NilEnvironment implements EnvironmentInterface {

    @Override
    public Result<VariableEntry> putVariable(String identifier, VariableEntry entry) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Result<FunctionEntry> putFunction(String identifier, FunctionEntry entry) {
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
    public Result<String> getFunctionReturnType(String id) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Result<Collection<DeclarationArgumentNode>> getFunctionArguments(String id) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Result<Collection<InterpretableNode>> getFunctionBody(String id) {
        return new IncorrectResult<>("Nil env cannot do this operation.");
    }

    @Override
    public Boolean variableIsDeclared(String id) {
        return false;
    }

    @Override
    public Boolean functionIsDeclared(String id) {
        return false;
    }
}
