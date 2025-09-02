/*package interpreter.writer;

import results.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCodeWriterTest {
    private static final Path filePath = Paths.get("./.testFiles/write/TestClass.java");
    @Test
    public void createWriterTest() {
        JavaCodeWriter writer = new JavaCodeWriter(filePath);
        Assertions.assertNotNull(writer);
    }
    @Test
    public void writeWriterTest() {
        JavaCodeWriter writer = new JavaCodeWriter(filePath, "TestClass");
        Result writeResult = writer.writeCode("System.out.println(\"Hello World\");");
        Assertions.assertTrue(writeResult.isSuccessful());
    }
}


 */
