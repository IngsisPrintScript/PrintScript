package com.ingsis.printscript.formatter.yamlAnalizer;

import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;

public class ReadRules implements ReadRulesInterface {

    private final Path configFile;

    public ReadRules() {
        this.configFile = null;
    }
    public ReadRules(Path configFile) {
        this.configFile = configFile;
    }

    @Override
    public HashMap<String, Object> readRules() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream =
                    (configFile == null)
                            ? ReadRules.class.getResourceAsStream("/Rules.yaml")
                            : new FileInputStream(configFile.toFile())) {

                if (inputStream == null) {
                    throw new RuntimeException("Did not find Rules.yaml");
                }
                return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error reading: " + configFile, e);
        }
    }
}
