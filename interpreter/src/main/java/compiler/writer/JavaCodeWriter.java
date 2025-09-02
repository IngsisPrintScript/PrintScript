package compiler.writer;

import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record JavaCodeWriter(Path path, String className) implements CodeWriterInterface {
    private static Boolean firstWrite = true;


    public JavaCodeWriter(Path path) {
        this(path, "TranspiledCode");
    }

    @Override
    public Result<Path> writeCode(String code) {
        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            String wrappedCode =
                    """
                    public class %s {
                        public static void main(String[] args) {
                            %s
                        }
                    }
                    """.formatted(className(), code);
            if (Files.exists(path) && firstWrite) {
                Files.delete(path);
                firstWrite = false;
            } else if (Files.exists(path)) {
                String existingCode = Files.readString(path);

                // Regex to capture the body of main
                Pattern pattern = Pattern.compile(
                        "(public\\s+static\\s+void\\s+main\\s*\\([^)]*\\)\\s*\\{)([\\s\\S]*?)(\\})"
                );
                Matcher matcher = pattern.matcher(existingCode);

                if (matcher.find()) {
                    // Group 1 = "public static void main(...) {"
                    // Group 2 = existing body
                    // Group 3 = closing brace of main

                    String newBody = matcher.group(2) + "    " + code + System.lineSeparator();
                    String updated = matcher.group(1) + newBody + matcher.group(3);

                    // Replace only the main method
                    existingCode = matcher.replaceFirst(
                            Matcher.quoteReplacement(updated)
                    );

                    Files.writeString(path, existingCode, StandardOpenOption.TRUNCATE_EXISTING);
                    return new CorrectResult<>(path);
                } else {
                    return new IncorrectResult<>("Could not find main method");
                }
            }
            Files.writeString(path, wrappedCode);
            return new CorrectResult<>(path);
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
    }
}
