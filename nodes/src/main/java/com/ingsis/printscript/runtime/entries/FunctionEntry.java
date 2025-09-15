/*
 * My Project
 */

package com.ingsis.printscript.runtime.entries;

import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.visitor.InterpretableNode;
import java.util.Collection;
import java.util.Collections;

public record FunctionEntry(
        String returnType,
        Collection<DeclarationArgumentNode> arguments,
        Collection<InterpretableNode> body) {
    public FunctionEntry {
        arguments = Collections.unmodifiableCollection(arguments);
        body = Collections.unmodifiableCollection(body);
    }

    @Override
    public Collection<DeclarationArgumentNode> arguments() {
        return Collections.unmodifiableCollection(arguments);
    }

    @Override
    public Collection<InterpretableNode> body() {
        return Collections.unmodifiableCollection(body);
    }
}
