/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class YamlRuleStatusProviderTest {

    @Test
    void booleanValueIsReadAsBoolean() throws Exception {
        Path tmp = Files.createTempFile("rules", ".yml");
        Files.writeString(tmp, "ruleA: true\n");

        YamlRuleStatusProvider provider = new YamlRuleStatusProvider(tmp);
        assertTrue(provider.getRuleStatus("ruleA"));
        assertFalse(provider.getRuleStatus("not-found"));
    }

    @Test
    void stringBooleanIsParsed() throws Exception {
        Path tmp = Files.createTempFile("rules", ".yml");
        Files.writeString(tmp, "ruleB: \"true\"\n");

        YamlRuleStatusProvider provider = new YamlRuleStatusProvider(tmp);
        assertTrue(provider.getRuleStatus("ruleB"));
    }

    @Test
    void nonBooleanValueThrowsWhenAskedAsStatus() throws Exception {
        Path tmp = Files.createTempFile("rules", ".yml");
        Files.writeString(tmp, "ruleC: 123\n");

        YamlRuleStatusProvider provider = new YamlRuleStatusProvider(tmp);
        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> provider.getRuleStatus("ruleC"));
        assertTrue(ex.getMessage().contains("not a boolean"));
    }

    @Test
    void getRuleValueReturnsValueOrNullOrThrowsOnWrongType() throws Exception {
        Path tmp = Files.createTempFile("rules", ".yml");
        Files.writeString(tmp, "strRule: hello\nintRule: 42\n");

        YamlRuleStatusProvider provider = new YamlRuleStatusProvider(tmp);
        String s = provider.getRuleValue("strRule", String.class);
        assertEquals("hello", s);

        Integer i = provider.getRuleValue("intRule", Integer.class);
        assertEquals(42, i.intValue());

        assertNull(provider.getRuleValue("missing", String.class));

        assertThrows(
                ClassCastException.class, () -> provider.getRuleValue("strRule", Integer.class));
    }

    @Test
    void emptyYamlFileThrowsIllegalState() throws IOException {
        Path tmp = Files.createTempFile("rules-empty", ".yml");
        // write empty content
        Files.writeString(tmp, "");

        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> new YamlRuleStatusProvider(tmp));
        assertTrue(ex.getMessage().contains("YAML file is empty or invalid"));
    }

    @Test
    void missingFileThrowsUncheckedIOException() {
        Path missing = Path.of("non-existent-file-12345.yml");
        assertThrows(RuntimeException.class, () -> new YamlRuleStatusProvider(missing));
    }
}
