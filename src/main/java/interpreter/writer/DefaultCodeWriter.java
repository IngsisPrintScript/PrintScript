package interpreter.writer;

import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.nio.file.Files;
import java.nio.file.Path;

public record DefaultCodeWriter(Path path) implements CodeWriterInterface {

    @Override
    public Result writeCode(String code) {
        try {
            Files.writeString(path, code);
            return new CorrectResult<>(path.toAbsolutePath().toString());
        } catch (Exception e) {
            return new IncorrectResult(e.getMessage());
        }
    }
}
