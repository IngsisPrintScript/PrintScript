/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.services.ExecuteService;
import com.ingsis.engine.services.FormatService;
import com.ingsis.engine.services.LintService;
import com.ingsis.engine.versions.Version;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;
import com.ingsis.utils.result.Result;
import java.io.InputStream;
import java.io.Writer;

public class DefaultEngine implements Engine {
  private final ExecuteService executeService;
  private final FormatService formatService;
  private final LintService lintService;

  public DefaultEngine(
      ExecuteService executeService, FormatService formatService, LintService lintService) {
    this.executeService = executeService;
    this.formatService = formatService;
    this.lintService = lintService;
  }

  @Override
  public Result<String> interpret(
      Version version, OutputEmitter emitter, InputSupplier supplier, InputStream in) {
    return executeService.execute(version, emitter, supplier, in);
  }

  @Override
  public Result<String> format(
      InputStream in, InputStream config, Writer writer, Version version) {
    return formatService.format(version, in, config, writer);
  }

  @Override
  public Result<String> analyze(InputStream in, InputStream config, Version version) {
    return lintService.lint(version, config, in);
  }
}
