package com.ingsis.utils.rule.status.provider.factories;

import java.io.InputStream;

import com.ingsis.utils.rule.status.provider.RuleStatusProvider;

public interface RuleStatusProviderFactory {
  RuleStatusProvider createDefaultRuleStatusProvider(InputStream inputStream);
}
