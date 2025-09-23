/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import com.ingsis.types.Types;

public sealed interface VariableEntry permits DefaultVariableEntry {
    Types type();

    Object value();
}
