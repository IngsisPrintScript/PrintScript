/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider; /*
                                                * My Project
                                                */

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public final class YamlRuleStatusProvider implements RuleStatusProvider {
    private final Map<String, Object> rulesMap;

    public YamlRuleStatusProvider(Path yamlPath) {
        Map<String, Object> loaded = loadYamlRules(yamlPath);
        this.rulesMap = loaded;
    }

    private Map<String, Object> loadYamlRules(Path yamlPath) {
        try (InputStream in = Files.newInputStream(yamlPath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(in);
            if (data == null) {
                throw new IllegalStateException("YAML file is empty or invalid: " + yamlPath);
            }
            return data;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read YAML file: " + yamlPath, e);
        }
    }

    @Override
    public Boolean getRuleStatus(String ruleName) {
        Object value = rulesMap.get(ruleName);
        if (value == null) {
            return false;
        }

        if (value instanceof Boolean boolValue) {
            return boolValue;
        }

        if (value instanceof String strValue) {
            return Boolean.parseBoolean(strValue);
        }

        throw new IllegalStateException(
                "Rule '"
                        + ruleName
                        + "' is not a boolean value (found "
                        + value.getClass().getSimpleName()
                        + ")");
    }

    @Override
    public <T> T getRuleValue(String ruleName, Class<T> type) {
        Object value = rulesMap.get(ruleName);
        if (value == null) {
            return null;
        }

        if (!type.isInstance(value)) {
            throw new ClassCastException(
                    "Rule '"
                            + ruleName
                            + "' is of type "
                            + value.getClass().getSimpleName()
                            + " but expected "
                            + type.getSimpleName());
        }

        return type.cast(value);
    }
}
