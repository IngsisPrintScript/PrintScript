package interpreter.writer;

import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.nio.file.Files;
import java.nio.file.Path;

public record DefaultJavaCodeWriter(Path path) implements CodeWriterInterface {

    @Override
    public Result writeCode(String code) {
        try {
            String wrappedCode =
                    """
                    public class TranspiledCode {
                        public static void main(String[] args) {
                            %s
                        }
                    }
                    """.formatted(code);
            Files.writeString(path, wrappedCode);
            return new CorrectResult<>(path.toAbsolutePath().toString());
        } catch (Exception e) {
            return new IncorrectResult(e.getMessage());
        }
    }
}
