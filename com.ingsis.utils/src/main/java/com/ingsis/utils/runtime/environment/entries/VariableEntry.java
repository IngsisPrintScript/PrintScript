package com.ingsis.utils.runtime.environment.entries;

/*
 * My Project
 */


import com.ingsis.utils.type.types.Types;

public sealed interface VariableEntry permits DefaultVariableEntry {
    Types type();

    Boolean isMutable();

    Object value();
}
