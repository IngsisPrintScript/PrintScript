/*
 * My Project
 */

package com.ingsis.utils.metachar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetaCharTest {

    private MetaChar a;
    private MetaChar b;

    @BeforeEach
    public void setUp() {
        a = new MetaChar('H', 1, 1);
        b = new MetaChar('i', 1, 2);
    }

    @Test
    public void accessorsReturnProvidedValues() {
        assertEquals(Character.valueOf('H'), a.character());
        assertEquals(Integer.valueOf(1), a.line());
        assertEquals(Integer.valueOf(1), a.column());
    }

    @Test
    public void equalsAndHashCodeRespectState() {
        MetaChar copy = new MetaChar('H', 1, 1);
        assertEquals(a, copy);
        assertEquals(a.hashCode(), copy.hashCode());
    }

    @Test
    public void supportsNullValues() {
        MetaChar nullChar = new MetaChar(null, null, null);
        assertNull(nullChar.character());
        assertNull(nullChar.line());
        assertNull(nullChar.column());
        assertNotNull(nullChar.toString());
    }
}
