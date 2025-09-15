package com.ingsis.printscript.formatter;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

public class FormatterTest {


    @TempDir
    Path tempDir;

    private Formatter formatter;

    @BeforeEach
    void setUp() throws Exception {
        formatter = new Formatter();

        Path rulesFile = tempDir.resolve("Rules.yaml");
        String yamlContent = """
                ColonSpacingBefore: true
                ColonSpacingAfter: true
                SpaceBeforeAssignation: true
                SpaceAfterAssignation: true
                JumpsBeforePrint: 1
                """;
        Files.writeString(rulesFile, yamlContent);

        formatter.setInputFile(tempDir.resolve("dummy.txt"));
        formatter.setInputFile(rulesFile);
    }

    @Test
    void testCallEmptyFile() throws Exception {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);

        formatter.setInputFile(emptyFile);
        Result<Path> result = formatter.call();

        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertEquals(emptyFile, result.result());
    }

    @Test
    void testFormatLetStatementNode() {
        TypeNode typeNode = new TypeNode("int");
        IdentifierNode idNode = new IdentifierNode("x");

        AscriptionNode ascriptionNode = new AscriptionNode();
        ascriptionNode.setType(typeNode);
        ascriptionNode.setIdentifier(idNode);

        LetStatementNode letNode = new LetStatementNode();
        letNode.setAscription(ascriptionNode);

        Result<String> formatted = formatter.format(letNode);

        Assertions.assertInstanceOf(CorrectResult.class, formatted);
        String expected = "let x : int;\n";
        Assertions.assertEquals(expected, formatted.result());
    }

    @Test
    void testCallWithContentFile() throws Exception {
        Path fileWithContent = Path.of("C:\\Users\\santi\\Faculty\\Ingsis\\PrintScript\\formatter\\src\\test\\resources\\toFormatTest.txt");
        Assertions.assertTrue(Files.exists(fileWithContent), "El archivo de prueba no existe.");

        formatter.setInputFile(fileWithContent);

        Result<Path> result = formatter.call();

        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertEquals(fileWithContent, result.result());

        String fileContent = Files.readString(fileWithContent);

        Assertions.assertTrue(fileContent.contains("let name : String = \"a\";"));
        Assertions.assertTrue(fileContent.contains("let file : String = \"b\";"));
        Assertions.assertTrue(fileContent.contains("println( \"a\" );"));
        Assertions.assertTrue(fileContent.contains("println( name );"));
        Assertions.assertTrue(fileContent.contains("println(name+file);"));
    }
}
