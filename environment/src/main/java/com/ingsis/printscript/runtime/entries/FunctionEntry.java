package com.ingsis.printscript.runtime.entries;

import com.ingsis.printscript.astnodes.statements.function.argument.DeclarationArgumentNode;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;

import java.util.Collection;

public record FunctionEntry(
        String returnType,
        Collection<DeclarationArgumentNode> arguments,
        Collection<InterpretableNode> body
){}
