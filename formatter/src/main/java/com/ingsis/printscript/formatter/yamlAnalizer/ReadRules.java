package com.ingsis.printscript.formatter.yamlAnalizer;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

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

        Path defaultRulesPath = Path.of("formatter", "src", "main", "resources", "Rules.yaml");
        try (InputStream inputStream = (configFile != null)
                ? Files.newInputStream(configFile)
                : Files.newInputStream(defaultRulesPath)) {

            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error reading Rules.yaml", e);
        }
    }


    public static void main(String[] args) {
        ReadRules loader = new ReadRules();
        HashMap<String, Object> rules = loader.readRules();
        System.out.println(rules);
    }
}
