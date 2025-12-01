/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.utils.result.Result;
import java.io.InputStream;
import java.io.Writer;

public interface Engine {
  public Result<String> interpret(InputStream inputStream);

  public Result<String> format(InputStream inputStream, InputStream config, Writer writer);

  public Result<String> analyze(InputStream inputStream, InputStream config);
}
