package com.ingsis.printscript.linter;

import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.config.ConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ConfigLoaderTest {

    @Test
    void testLoadYamlConfig() throws IOException {
        String yamlContent = """
            naming:
              enabled: true
              style: CAMEL
            """;

        Path tempFile = Files.createTempFile("config", ".yml");
        Files.writeString(tempFile, yamlContent);

        AnalyzerConfig config = ConfigLoader.load(tempFile);

        Assertions.assertNotNull(config);
        Assertions.assertTrue(config.naming().enabled());
        Assertions.assertEquals(AnalyzerConfig.CaseStyle.CAMEL, config.naming().style());
    }

    @Test
    void testLoadJsonConfig() throws IOException {
        String jsonContent = """
            {
              "naming": {
                "enabled": true,
                "style": "SNAKE"
              }
            }
            """;

        Path tempFile = Files.createTempFile("config", ".json");
        Files.writeString(tempFile, jsonContent);

        AnalyzerConfig config = ConfigLoader.load(tempFile);

        Assertions.assertNotNull(config);
        Assertions.assertTrue(config.naming().enabled());
        Assertions.assertEquals(AnalyzerConfig.CaseStyle.SNAKE, config.naming().style());
    }

    @Test
    void testFallbackToDefaultsWhenInvalidFile() throws IOException {
        Path tempFile = Files.createTempFile("config", ".yml");
        Files.writeString(tempFile, "::: contenido inválido :::");

        AnalyzerConfig config = ConfigLoader.load(tempFile);

        Assertions.assertNotNull(config);
        Assertions.assertEquals(AnalyzerConfig.defaults().naming().style(), config.naming().style());
        Assertions.assertEquals(AnalyzerConfig.defaults().naming().enabled(), config.naming().enabled());
    }

    @Test
    void testFallbackToDefaultsWhenFileNotFound() {
        Path fakePath = Path.of("no-existe.yml");

        AnalyzerConfig config = ConfigLoader.load(fakePath);

        Assertions.assertNotNull(config);
        Assertions.assertEquals(AnalyzerConfig.defaults().naming().style(), config.naming().style());
        Assertions.assertEquals(AnalyzerConfig.defaults().naming().enabled(), config.naming().enabled());
    }
}
