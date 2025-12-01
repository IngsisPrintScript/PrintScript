package com.ingsis.utils.rule.status.provider.factories;

import java.io.InputStream;
import java.util.List;

import com.ingsis.utils.rule.status.provider.JsonRuleStatusProvider;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.rule.status.provider.RuleStatusProviderRegistry;
import com.ingsis.utils.rule.status.provider.YamlRuleStatusProvider;

public class InMemoryRuleStatusProviderFactory implements RuleStatusProviderFactory {

  @Override
  public RuleStatusProvider createDefaultRuleStatusProvider(InputStream inputStream) {
    RuleStatusProvider yamlRuleStatusProvider = new YamlRuleStatusProvider();
    RuleStatusProvider jsonRuleStatusProvider = new JsonRuleStatusProvider();
    RuleStatusProvider ruleStatusProviderRegistry = new RuleStatusProviderRegistry(
        List.of(yamlRuleStatusProvider, jsonRuleStatusProvider));
    ruleStatusProviderRegistry.loadRules(inputStream);
    return ruleStatusProviderRegistry;
  }

}
