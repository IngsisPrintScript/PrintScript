package interpreter.writer;

import results.Result;

import java.nio.file.Path;

public interface CodeWriterInterface {
    Result<Path> writeCode(String code);
}
