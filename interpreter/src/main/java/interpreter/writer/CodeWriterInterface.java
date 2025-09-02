package interpreter.writer;

import java.nio.file.Path;
import results.Result;

public interface CodeWriterInterface {
  Result<Path> writeCode(String code);
}
