/*
 * My Project
 */

package com.ingsis.printscript.formatter.yamlAnalizer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;

public class ReadRules implements ReadRulesInterface {
    @Override
    public HashMap<String, Object> readRules() {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;

        try {
            inputStream = ReadRules.class.getResourceAsStream("/Rules.yaml");
            return yaml.load(inputStream);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("Error closing stream: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        ReadRules loader = new ReadRules();
        HashMap<String, Object> rules = loader.readRules();
        System.out.println(rules);
    }
}
