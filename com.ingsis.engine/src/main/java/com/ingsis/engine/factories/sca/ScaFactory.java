/*
 * My Project
 */

package com.ingsis.engine.factories.sca;

import com.ingsis.runtime.Runtime;
import com.ingsis.sca.ProgramSca;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;

import java.io.InputStream;

public interface ScaFactory {
  public ProgramSca fromFile(
      InputStream in,
      RuleStatusProvider ruleStatusProvider,
      Runtime runtime);
}
