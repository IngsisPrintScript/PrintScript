package interpreter.executor;

import responses.Result;

import java.nio.file.Path;

public interface CodeExecutorInterface {
    Result executeCode(Path path);
}
