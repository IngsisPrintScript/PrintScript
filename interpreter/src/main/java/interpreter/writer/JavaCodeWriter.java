package interpreter.writer;

import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;

import java.nio.file.Files;
import java.nio.file.Path;

public record JavaCodeWriter(Path path, String className) implements CodeWriterInterface {

    public JavaCodeWriter(Path path) {
        this(path, "TranspiledCode");
    }

    @Override
    public Result writeCode(String code) {
        try {
            String wrappedCode =
                    """
                    public class %s {
                        public static void main(String[] args) {
                            %s
                        }
                    }
                    """.formatted(className(), code);
            Files.writeString(path, wrappedCode);
            return new CorrectResult<>(path);
        } catch (Exception e) {
            return new IncorrectResult(e.getMessage());
        }
    }
}
