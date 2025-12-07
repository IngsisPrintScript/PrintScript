/*
 * My Project
 */

package com.ingsis.formatter.factories;

import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import java.io.InputStream;
import java.io.Writer;

public interface FormatterFactory {
  public ProgramFormatter fromFile(
      InputStream inputStream,
      Runtime runtime,
      RuleStatusProvider ruleStatusProvider,
      Writer writer);
}
