package interpreter.transpiler;


import common.Node;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import factories.NodeFactory;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import interpreter.transpiler.visitor.JavaTranspilationVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class JavaTranspilationVisitorTest {
    private static final NodeFactory nodeFactory = new NodeFactory();
    private static Map<Node, String> treeCodeMap;

    @BeforeAll
    public static void setup() {
        LetStatementNode letNodeOne = (LetStatementNode) nodeFactory.createLetStatementNode();
        AscriptionNode ascriptionNode = (AscriptionNode) nodeFactory.createAscriptionNode();
        ascriptionNode.setIdentifier((IdentifierNode) nodeFactory.createIdentifierNode("identifier"));
        ascriptionNode.setType((TypeNode) nodeFactory.createTypeNode("Type"));
        letNodeOne.setAscription(ascriptionNode);
        LetStatementNode letNodeTwo = (LetStatementNode) nodeFactory.createLetStatementNode();
        letNodeTwo.setAscription(ascriptionNode);
        letNodeTwo.setExpression((LiteralNode) nodeFactory.createLiteralNode("\"placeholder\""));
        PrintStatementNode printNode = (PrintStatementNode) nodeFactory.createPrintlnStatementNode();
        printNode.setExpression((LiteralNode) nodeFactory.createLiteralNode("\"Hello, World!\""));
        treeCodeMap = Map.ofEntries(
                Map.entry(letNodeOne, "Type identifier;"),
                Map.entry(letNodeTwo, "Type identifier = \"placeholder\";"),
                Map.entry(printNode, "System.out.println(\"Hello, World!\");")
        );
    }

    @Test
    public void createJavaTranspilationVisitorTest() {
        JavaTranspilationVisitor visitor = new JavaTranspilationVisitor();
        Assertions.assertNotNull(visitor);
    }
    @Test
    public void visitJavaTranspilationVisitorTest() {
        JavaTranspilationVisitor visitor = new JavaTranspilationVisitor();
        for (Node tree : treeCodeMap.keySet()) {
            Result treeVisitResult = tree.accept(visitor);
            if (!treeVisitResult.isSuccessful()) {
                Assertions.fail(((IncorrectResult) treeVisitResult).errorMessage());
            }
            String code = ((CorrectResult<String>) treeVisitResult).result();
            Assertions.assertEquals(treeCodeMap.get(tree), code);
        }
    }
}