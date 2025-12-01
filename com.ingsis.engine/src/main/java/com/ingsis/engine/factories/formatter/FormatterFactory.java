/*
 * My Project
 */

package com.ingsis.engine.factories.formatter;

import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;

import java.io.InputStream;

public interface FormatterFactory {
  public ProgramFormatter fromFile(
      InputStream inputStream,
      Runtime runtime,
      RuleStatusProvider ruleStatusProvider);
}
