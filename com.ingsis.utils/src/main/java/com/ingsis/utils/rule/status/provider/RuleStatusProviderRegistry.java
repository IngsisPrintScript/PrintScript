package com.ingsis.utils.rule.status.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public final class RuleStatusProviderRegistry implements RuleStatusProvider {

  private final List<RuleStatusProvider> providers;
  private RuleStatusProvider activeProvider;

  public RuleStatusProviderRegistry(List<RuleStatusProvider> providers) {
    this.providers = providers;
  }

  public void loadRules(InputStream inputStream) {
    try {
      byte[] buffer = inputStream.readAllBytes();

      for (RuleStatusProvider provider : providers) {
        ByteArrayInputStream probe = new ByteArrayInputStream(buffer);

        if (provider.canReadInputFormat(probe)) {
          provider.loadRules(new ByteArrayInputStream(buffer));
          this.activeProvider = provider;
          return;
        }
      }

      throw new IllegalStateException("No rule provider could read the input format");

    } catch (IOException e) {
      throw new UncheckedIOException("Could not read rules stream", e);
    }
  }

  public Boolean getRuleStatus(String ruleName) {
    if (activeProvider == null) {
      throw new IllegalStateException("Rules have not been loaded");
    }
    return activeProvider.getRuleStatus(ruleName);
  }

  public <T> T getRuleValue(String ruleName, Class<T> type) {
    if (activeProvider == null) {
      throw new IllegalStateException("Rules have not been loaded");
    }
    return activeProvider.getRuleValue(ruleName, type);
  }

  @Override
  public Boolean canReadInputFormat(InputStream inputStream) {
    for (RuleStatusProvider ruleStatusProvider : providers) {
      if (ruleStatusProvider.canReadInputFormat(inputStream)) {
        return true;
      }
    }
    return false;
  }
}
