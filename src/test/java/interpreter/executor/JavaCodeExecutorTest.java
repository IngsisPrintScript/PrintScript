package interpreter.executor;

import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import interpreter.writer.JavaCodeWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;


public class JavaCodeExecutorTest {
    private final static Path filePath = Paths.get("./.testFiles/ExecuteTestClassName.java");

    @BeforeAll
    public static void setup() {
        JavaCodeWriter writer = new JavaCodeWriter(filePath, "ExecuteTestClassName");
        writer.writeCode("System.out.println(\"Hello, World!\");");
    }

    @Test
    public void createExecutorTest() {
        JavaCodeExecutor executor = new JavaCodeExecutor("ExecuteTestClassName");
        Assertions.assertNotNull(executor);
    }
    @Test
    public void executeExecutorTest() {
        JavaCodeExecutor executor = new JavaCodeExecutor("ExecuteTestClassName");
        Result executeResult = executor.executeCode(filePath);
        Assertions.assertTrue(executeResult.isSuccessful());
    }
}
