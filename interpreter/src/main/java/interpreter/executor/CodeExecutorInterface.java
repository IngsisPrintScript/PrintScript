package interpreter.executor;

import results.Result;

import java.nio.file.Path;

public interface CodeExecutorInterface {
    Result<String> executeCode(Path path);
}
