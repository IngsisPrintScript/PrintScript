/*
 * My Project
 */

package com.ingsis.runtime.environment.entries.factories;

import com.ingsis.runtime.environment.entries.DefaultVariableEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;

public final class DefaultEntryFactory implements EntryFactory {
    @Override
    public VariableEntry createVariableEntry(Types type, Object value) {
        return new DefaultVariableEntry(type, value);
    }
}
