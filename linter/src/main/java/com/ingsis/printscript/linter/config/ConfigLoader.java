/*
 * My Project
 */

package com.ingsis.printscript.linter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class ConfigLoader {
    private ConfigLoader() {}

    public static AnalyzerConfig load(Path path) {
        try {
            String lower = path.toString().toLowerCase(Locale.US);
            ObjectMapper mapper =
                    lower.endsWith(".yml") || lower.endsWith(".yaml")
                            ? new ObjectMapper(new YAMLFactory())
                            : new ObjectMapper();
            return mapper.readValue(Files.readAllBytes(path), AnalyzerConfig.class);
        } catch (IOException e) {
            // fallback a defaults si no hay config/archivo inválido
            return AnalyzerConfig.defaults();
        }
    }
}
