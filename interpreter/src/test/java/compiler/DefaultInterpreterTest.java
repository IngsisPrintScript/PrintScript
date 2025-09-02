package compiler;


import common.Node;
import declaration.TypeNode;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import results.Result;
import declaration.AscriptionNode;
import factories.NodeFactory;
import compiler.executor.CodeExecutorInterface;
import compiler.executor.JavaCodeExecutor;
import compiler.transpiler.DefaultJavaTranspiler;
import compiler.transpiler.TranspilerInterface;
import compiler.writer.CodeWriterInterface;
import compiler.writer.JavaCodeWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import statements.LetStatementNode;
import statements.PrintStatementNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class DefaultInterpreterTest {
    private static TranspilerInterface transpiler;
    private static CodeWriterInterface writer;
    private static CodeExecutorInterface executor;
    private static Map<Node, String> treeCodeMap;
    private static final NodeFactory nodeFactory = new NodeFactory();
    private static final Path filePath = Paths.get("./.testFiles/interpret/ClassTest.java");

    @BeforeAll
    public static void setUp() {
        transpiler = new DefaultJavaTranspiler();
        writer = new JavaCodeWriter(filePath, "ClassTest");
        executor = new JavaCodeExecutor("ClassTest");
        LetStatementNode letNodeOne = (LetStatementNode) nodeFactory.createLetStatementNode();
        AscriptionNode ascriptionNode = (AscriptionNode) nodeFactory.createAscriptionNode();
        ascriptionNode.setIdentifier((IdentifierNode) nodeFactory.createIdentifierNode("identifier"));
        ascriptionNode.setType((TypeNode) nodeFactory.createTypeNode("String"));
        letNodeOne.setAscription(ascriptionNode);
        LetStatementNode letNodeTwo = (LetStatementNode) nodeFactory.createLetStatementNode();
        letNodeTwo.setAscription(ascriptionNode);
        letNodeTwo.setExpression((LiteralNode) nodeFactory.createLiteralNode("\"placeholder\""));
        PrintStatementNode printNode = (PrintStatementNode) nodeFactory.createPrintlnStatementNode();
        printNode.setExpression((LiteralNode) nodeFactory.createLiteralNode("\"Hello, World!\""));
        treeCodeMap = Map.ofEntries(
                Map.entry(letNodeOne, "String identifier;"),
                Map.entry(letNodeTwo, "String identifier = \"placeholder\";"),
                Map.entry(printNode, "System.out.println(\"Hello, World!\");")
        );
    }

    @Test
    public void createInterpreterTest() {
        DefaultCompiler interpreter = new DefaultCompiler(transpiler, writer, executor);
        Assertions.assertNotNull(interpreter);
    }

    @Test
    public void interpretInterpreterTest() {
        DefaultCompiler interpreter = new DefaultCompiler(transpiler, writer, executor);
        for (Node tree: treeCodeMap.keySet()) {
            Result interpretResult = interpreter.compile(tree);
            Assertions.assertTrue(interpretResult.isSuccessful());
            try {
                Files.deleteIfExists(filePath);
            } catch (Exception ignore) {
            }
        }
    }
}
