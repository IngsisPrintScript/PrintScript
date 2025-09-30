/*
 * My Project
 */

package com.ingsis.runtime.environment.entries.factories;

import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;

public interface EntryFactory {
    VariableEntry createVariableEntry(Types type, Object value);
}
