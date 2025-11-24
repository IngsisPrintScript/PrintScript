package com.ingsis.runtime.environment.entries;

import com.ingsis.types.Types;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultVariableEntryMoreTest {

    @Test
    void oneArgConstructorSetsNullValue() {
        DefaultVariableEntry entry = new DefaultVariableEntry(Types.STRING, true);
        assertEquals(Types.STRING, entry.type());
        assertNull(entry.value());
        assertTrue(entry.isMutable());
    }
}
