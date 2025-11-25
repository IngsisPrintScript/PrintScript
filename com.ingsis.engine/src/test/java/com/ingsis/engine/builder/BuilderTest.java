/*
 * My Project
 */

package com.ingsis.engine.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.engine.CliEngine;
import com.ingsis.engine.versions.Version;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class BuilderTest {
    @Test
    void testCliEngineBuilder() {
        CliEngine engine =
                new CliEngine.CliEngineBuilder()
                        .command("format")
                        .file(Paths.get("test.ps"))
                        .formatConfig(Paths.get("rules.yml"))
                        .version(Version.V1_0)
                        .build();

        assertEquals("format", engine.command);
        assertEquals(Paths.get("test.ps"), engine.file);
        assertEquals(Paths.get("rules.yml"), engine.formatConfig);
        assertEquals(Version.V1_0, engine.version);
    }
}
