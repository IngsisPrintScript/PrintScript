/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import com.ingsis.types.Types;

public record DefaultVariableEntry(Types type, Object value) implements VariableEntry {}
