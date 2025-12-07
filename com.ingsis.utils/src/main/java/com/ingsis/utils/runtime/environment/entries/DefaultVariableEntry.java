/*
 * My Project
 */

package com.ingsis.utils.runtime.environment.entries;

import com.ingsis.utils.type.types.Types;

public record DefaultVariableEntry(Types type, Object value, Boolean isMutable)
        implements VariableEntry {
    public DefaultVariableEntry(Types type, Boolean isMutable) {
        this(type, null, isMutable);
    }
}
