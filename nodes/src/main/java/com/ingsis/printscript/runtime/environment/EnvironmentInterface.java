/*
 * My Project
 */

package com.ingsis.printscript.runtime.environment;

import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.entries.FunctionEntry;
import com.ingsis.printscript.runtime.entries.VariableEntry;
import com.ingsis.printscript.visitor.InterpretableNode;
import java.util.Collection;

public interface EnvironmentInterface {
    Result<VariableEntry> putVariable(String identifier, VariableEntry entry);

    Result<FunctionEntry> putFunction(String identifier, FunctionEntry entry);

    Result<VariableEntry> modifyVariableValue(String identifier, Object value);

    Result<String> getVariableType(String id);

    Result<Object> getVariableValue(String id);

    Result<String> getFunctionReturnType(String id);

    Result<Collection<DeclarationArgumentNode>> getFunctionArguments(String id);

    Result<Collection<InterpretableNode>> getFunctionBody(String id);

    Boolean variableIsDeclared(String id);

    Boolean functionIsDeclared(String id);
}
