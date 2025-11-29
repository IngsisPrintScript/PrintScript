/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import com.ingsis.utils.type.types.Types;

public sealed interface VariableEntry permits DefaultVariableEntry {
    Types type();

    Boolean isMutable();

    Object value();
}
