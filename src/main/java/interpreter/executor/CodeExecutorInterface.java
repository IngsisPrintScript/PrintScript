package interpreter.executor;

import common.responses.Result;

import java.nio.file.Path;

public interface CodeExecutorInterface {
    Result executeCode(Path path);
}
