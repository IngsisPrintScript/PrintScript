/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider.factories;

import com.ingsis.utils.rule.status.provider.JsonRuleStatusProvider;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.rule.status.provider.RuleStatusProviderRegistry;
import com.ingsis.utils.rule.status.provider.YamlRuleStatusProvider;
import java.io.InputStream;
import java.util.List;

public class InMemoryRuleStatusProviderFactory implements RuleStatusProviderFactory {

    @Override
    public RuleStatusProvider createDefaultRuleStatusProvider(InputStream inputStream) {
        RuleStatusProvider yamlRuleStatusProvider = new YamlRuleStatusProvider();
        RuleStatusProvider jsonRuleStatusProvider = new JsonRuleStatusProvider();
        RuleStatusProvider ruleStatusProviderRegistry =
                new RuleStatusProviderRegistry(
                        List.of(yamlRuleStatusProvider, jsonRuleStatusProvider));
        ruleStatusProviderRegistry.loadRules(inputStream);
        return ruleStatusProviderRegistry;
    }
}
