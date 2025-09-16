package com.ingsis.printscript.formatter.yamlAnalizer;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
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
        InputStream inputStream = null;
        try {
            if(configFile == null) {
                inputStream = ReadRules.class.getResourceAsStream("/Rules.yaml");
            }else {
                inputStream = new FileInputStream(configFile.toFile());
            }
            if (inputStream == null) {
                throw new RuntimeException("Did not found Rules.yml");
            }
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error reading Rules.yml", e);
        }
    }


    public static void main(String[] args) {
        ReadRules loader = new ReadRules();
        HashMap<String, Object> rules = loader.readRules();
        System.out.println(rules);
    }
}
