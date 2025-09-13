/*
 * My Project
 */

package com.ingsis.printscript.formatter.yamlAnalizer;

import java.io.InputStream;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;

public class ReadRules implements ReadRulesInterface {
    @Override
    public HashMap<String, Object> readRules() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ReadRules.class.getResourceAsStream("/Rules.yaml")) {
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
