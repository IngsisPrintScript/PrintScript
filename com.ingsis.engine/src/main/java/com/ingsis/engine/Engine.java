/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.utils.result.Result;
import java.io.InputStream;

public interface Engine {
    public Result<String> interpret(InputStream inputStream);

    public Result<String> format(InputStream inputStream);

    public Result<String> analyze(InputStream inputStream);
}
