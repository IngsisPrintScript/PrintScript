/*
 * My Project
 */

package com.ingsis.typer.identifier;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.typer.TypeGetter;
import com.ingsis.types.Types;

public final class DefaultIdentifierTypeGetter implements TypeGetter<IdentifierNode> {
    private final Runtime runtime;

    public DefaultIdentifierTypeGetter(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Types getType(IdentifierNode expressionNode) {
        String name = expressionNode.name();
        Result<VariableEntry> getVarResult = runtime.getCurrentEnvironment().getVariable(name);
        if (!getVarResult.isCorrect()) {
            return Types.UNDEFINED;
        }
        return getVarResult.result().type();
    }
}
