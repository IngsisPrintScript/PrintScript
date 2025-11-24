/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.types.Types;
import org.junit.jupiter.api.Test;

class DefaultVariableEntryTest {

    @Test
    void recordFieldsAreAccessible() {
        DefaultVariableEntry entry = new DefaultVariableEntry(Types.NUMBER, 42, false);
        assertEquals(Types.NUMBER, entry.type());
        assertEquals(42, entry.value());
        assertEquals(false, entry.isMutable());
    }

    @Test
    void twoArgConstructorSetsNullValue() {
        DefaultVariableEntry entry = new DefaultVariableEntry(Types.STRING, true);
        assertEquals(Types.STRING, entry.type());
        // value should be null when using the (type, isMutable) constructor
        assertEquals(null, entry.value());
        assertEquals(true, entry.isMutable());
    }
}
