/*
 * My Project
 */

package com.ingsis.metachar.string.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ingsis.metachar.MetaChar;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetaCharStringBuilderTest {

    private MetaChar m1;
    private MetaChar m2;
    private MetaChar m3;

    @BeforeEach
    public void setUp() {
        m1 = new MetaChar('H', 1, 1);
        m2 = new MetaChar('i', 1, 2);
        m3 = new MetaChar('!', 2, 1);
    }

    @Test
    public void emptyConstructorInitializesNullLineAndColumn() {
        MetaCharStringBuilder builder = new MetaCharStringBuilder();
        assertNull(builder.getLine());
        assertNull(builder.getColumn());
        assertEquals("", builder.getString());

        builder.append(m1);
        assertEquals(Integer.valueOf(1), builder.getLine());
        assertEquals(Integer.valueOf(1), builder.getColumn());
        assertEquals("H", builder.getString());
    }

    @Test
    public void constructorWithInitialListSetsLineColumnAndString() {
        List<MetaChar> initial = List.of(m1, m2);
        MetaCharStringBuilder builder = new MetaCharStringBuilder(initial);
        assertEquals(Integer.valueOf(1), builder.getLine());
        assertEquals(Integer.valueOf(1), builder.getColumn());
        assertEquals("Hi", builder.getString());

        builder.append(m3);
        assertEquals("Hi!", builder.getString());
        assertEquals(Integer.valueOf(1), builder.getLine());
        assertEquals(Integer.valueOf(1), builder.getColumn());
    }

    @Test
    public void appendSetsLineAndColumnWhenPreviouslyNull() {
        MetaCharStringBuilder builder = new MetaCharStringBuilder(new ArrayList<>());
        assertNull(builder.getLine());
        assertNull(builder.getColumn());

        builder.append(m3);
        assertNotNull(builder.getLine());
        assertNotNull(builder.getColumn());
        assertEquals(Integer.valueOf(2), builder.getLine());
        assertEquals(Integer.valueOf(1), builder.getColumn());
        assertEquals("!", builder.getString());
    }

    @Test
    public void getStringConcatenatesAllCharacters() {
        MetaCharStringBuilder builder = new MetaCharStringBuilder();
        builder.append(m1);
        builder.append(m2);
        builder.append(m3);
        assertEquals("Hi!", builder.getString());
    }
}
