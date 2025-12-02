/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

public final class JsonRuleStatusProvider implements RuleStatusProvider {

  private Map<String, Object> rulesMap;

  public JsonRuleStatusProvider(InputStream jsonInputStream) {
    this.rulesMap = loadJsonRules(jsonInputStream);
  }

  public JsonRuleStatusProvider() {
    this.rulesMap = new HashMap<>();
  }

  private Map<String, Object> loadJsonRules(InputStream jsonInputStream) {
    try (jsonInputStream) {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> data = mapper.readValue(jsonInputStream, new TypeReference<>() {
      });

      if (data == null) {
        throw new IllegalStateException("JSON input stream is empty or invalid");
      }

      return data;

    } catch (IOException e) {
      throw new UncheckedIOException("Could not read JSON InputStream", e);
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
  @SuppressWarnings("unchecked")
  public <T> T getRuleValue(String ruleName, Class<T> type) {
    Object value = rulesMap.get(ruleName);

    if (value == null) {
      return null; // same behavior as YAML version
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

    return (T) value;
  }

  @Override
  public Boolean canReadInputFormat(InputStream input) {
    try {
      byte[] bytes = input.readAllBytes();

      // rewind readable copy
      input.reset();

      for (byte b : bytes) {
        char c = (char) b;
        if (!Character.isWhitespace(c)) {
          return c == '{' || c == '[';
        }
      }

      return false; // empty file is not JSON

    } catch (IOException e) {
      throw new UncheckedIOException("Could not inspect rule input", e);
    }
  }

  @Override
  public void loadRules(InputStream inputStream) {
    rulesMap.clear();
    this.rulesMap = loadJsonRules(inputStream);
  }
}
