/*
 * My Project
 */

package com.ingsis.utils.runtime.type.typer.identifier;

import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultIdentifierTypeGetter implements TypeGetter<IdentifierNode> {
    private final Runtime runtime;

    public DefaultIdentifierTypeGetter(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Types getType(IdentifierNode expressionNode) {
        String name = expressionNode.name();
        Result<VariableEntry> getVarResult = runtime.getCurrentEnvironment().readVariable(name);
        if (!getVarResult.isCorrect()) {
            return Types.UNDEFINED;
        }
        return getVarResult.result().type();
    }
}
