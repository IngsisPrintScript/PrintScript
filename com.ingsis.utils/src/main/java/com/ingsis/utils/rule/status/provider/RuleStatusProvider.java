/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider;

import java.io.InputStream;

public interface RuleStatusProvider {
  public void loadRules(InputStream inputStream);

  public Boolean canReadInputFormat(InputStream inputStream);

  public Boolean getRuleStatus(String ruleName);

  public <T> T getRuleValue(String ruleName, Class<T> type);
}
