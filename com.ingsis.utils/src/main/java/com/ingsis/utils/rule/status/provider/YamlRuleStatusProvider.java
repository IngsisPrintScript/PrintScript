/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public final class YamlRuleStatusProvider implements RuleStatusProvider {
    private Map<String, Object> rulesMap;

    public YamlRuleStatusProvider(InputStream yamlInputStream) {
        this.rulesMap = loadYamlRules(yamlInputStream);
    }

    public YamlRuleStatusProvider() {
        this.rulesMap = new HashMap<>();
    }

    private Map<String, Object> loadYamlRules(InputStream yamlInputStream) {
        try (yamlInputStream) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(yamlInputStream);
            if (data == null) {
                throw new IllegalStateException("YAML input stream is empty or invalid");
            }
            return data;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read YAML InputStream", e);
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

    @Override
    public Boolean canReadInputFormat(InputStream inputStream) {
        try {
            inputStream.mark(64); // allow rewinding

            int ch;
            do {
                ch = inputStream.read();
            } while (ch != -1 && Character.isWhitespace(ch));

            inputStream.reset();

            return !(ch == '{' || ch == '[');

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public RuleStatusProvider loadRules(InputStream inputStream) {
        rulesMap.clear();
        this.rulesMap = loadYamlRules(inputStream);
        return this;
    }
}
