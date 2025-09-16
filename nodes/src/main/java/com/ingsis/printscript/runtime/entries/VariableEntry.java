/*
 * My Project
 */

package com.ingsis.printscript.runtime.entries;

public record VariableEntry(String type, Object value) {

    public VariableEntry(String type) {
        this(type, null);
    }

    public Boolean isInitialized() {
        return value != null;
    }
}
