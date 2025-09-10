package formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import results.Result;

import java.nio.file.Files;
import java.nio.file.Path;

public class FormatterRulesTest {

    @Test
    public void testFormatterCall() throws Exception {
        //Path inputPath = Path.of("/home/santiagogarrote/Faculty/Ingsis/PrintScript/formatter/src/main/resources/toFormat.txt");
        Path inputPath = Path.of("C:\\Users\\santi\\Faculty\\Ingsis\\PrintScript\\formatter\\src\\main\\resources\\toFormat.txt");

        String originalContent = Files.readString(inputPath);

        Formatter formatter = new Formatter();
        java.lang.reflect.Field field = Formatter.class.getDeclaredField("inputFile");
        field.setAccessible(true);
        field.set(formatter, inputPath);

        Result<String> result = formatter.call();

        String newContent = Files.readString(inputPath);
        Assertions.assertEquals(originalContent, newContent);
        System.out.println(newContent);
    }

}
