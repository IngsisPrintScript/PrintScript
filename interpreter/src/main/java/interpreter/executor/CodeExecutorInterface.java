package interpreter.executor;

import java.nio.file.Path;
import results.Result;

public interface CodeExecutorInterface {
  Result<String> executeCode(Path path);
}
