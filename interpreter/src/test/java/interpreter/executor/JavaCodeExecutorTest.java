package interpreter.executor;


import responses.Result;
import interpreter.writer.JavaCodeWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;


public class JavaCodeExecutorTest {
    private final static Path filePath = Paths.get("./.testFiles/execute/TestClassName.java");

    @BeforeAll
    public static void setup() {
        JavaCodeWriter writer = new JavaCodeWriter(filePath, "TestClassName");
        writer.writeCode("System.out.println(\"Hello, World!\");");
    }

    @Test
    public void createExecutorTest() {
        JavaCodeExecutor executor = new JavaCodeExecutor("TestClassName");
        Assertions.assertNotNull(executor);
    }
    @Test
    public void executeExecutorTest() {
        JavaCodeExecutor executor = new JavaCodeExecutor("TestClassName");
        Result executeResult = executor.executeCode(filePath);
        Assertions.assertTrue(executeResult.isSuccessful());
    }
}
