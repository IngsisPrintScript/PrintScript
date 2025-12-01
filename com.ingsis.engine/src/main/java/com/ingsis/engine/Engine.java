/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.versions.Version;
import com.ingsis.utils.result.Result;
import java.io.InputStream;
import java.io.Writer;

public interface Engine {
  public Result<String> interpret(InputStream inputStream, Version version);

  public Result<String> format(InputStream inputStream, InputStream config, Writer writer, Version version);

  public Result<String> analyze(InputStream inputStream, InputStream config, Version version);
}
