/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider.factories;

import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import java.io.InputStream;

public interface RuleStatusProviderFactory {
    RuleStatusProvider createDefaultRuleStatusProvider(InputStream inputStream);
}
