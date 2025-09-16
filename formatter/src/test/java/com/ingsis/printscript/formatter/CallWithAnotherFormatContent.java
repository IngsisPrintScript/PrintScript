package com.ingsis.printscript.formatter;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

public class CallWithAnotherFormatContent {

    @TempDir
    Path tempDir;

    @Test
    void callWithFileFormatsContent() throws Exception {
        Formatter formatter = new Formatter();
        Path original = Path.of(getClass().getClassLoader()
                .getResource("toFormatTest.txt").toURI());
        Path testFile = tempDir.resolve("toFormatTest.txt");
        Files.copy(original, testFile);
        Path rulesFile = tempDir.resolve("Rules.yaml");
        String yamlContent = """
                ColonSpacingBefore: false
                ColonSpacingAfter: false
                SpaceBeforeAssignation: false
                SpaceAfterAssignation: false
                JumpsBeforePrint: 1
                """;
        Files.writeString(rulesFile, yamlContent);
        formatter.setInputFile(testFile);
        formatter.setRulesFile(rulesFile);
        Result<Path> result = formatter.call();

        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertEquals(testFile, result.result());
        String content = Files.readString(testFile);
        Assertions.assertTrue(content.contains("let"));
    }


}
