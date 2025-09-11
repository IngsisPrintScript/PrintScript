package com.ingsis.printscript.compiler.executor;

import com.ingsis.printscript.results.Result;

import java.nio.file.Path;

public interface CodeExecutorInterface {
    Result<String> executeCode(Path path);
}
