package interpreter.writer;

import responses.Result;

import java.nio.file.Path;

public interface CodeWriterInterface {
    Result<Path> writeCode(String code);
}
