/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.Test;

class DefaultVariableEntryMoreTest {

    @Test
    void oneArgConstructorSetsNullValue() {
        DefaultVariableEntry entry = new DefaultVariableEntry(Types.STRING, true);
        assertEquals(Types.STRING, entry.type());
        assertNull(entry.value());
        assertTrue(entry.isMutable());
    }
}
