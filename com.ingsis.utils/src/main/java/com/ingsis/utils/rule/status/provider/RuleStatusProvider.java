/*
 * My Project
 */

package com.ingsis.utils.rule.status.provider;

public interface RuleStatusProvider {
  public Boolean getRuleStatus(String ruleName);

  public <T> T getRuleValue(String ruleName, Class<T> type);
}
