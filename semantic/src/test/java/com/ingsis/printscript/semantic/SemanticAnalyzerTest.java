package com.ingsis.printscript.semantic;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.enforcers.SemanticRulesChecker;
import com.ingsis.printscript.syntactic.Syntactic;
import com.ingsis.printscript.syntactic.ast.builders.cor.ChanBuilder;
import com.ingsis.printscript.tokenizers.factories.TokenizerFactory;
import com.ingsis.printscript.tokens.TokenInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SemanticAnalyzerTest {

    static class CodeRepository implements PeekableIterator<Character> {
        private int index = 0;
        private final String content;

        public CodeRepository(Path path) {
            try {
                this.content = Files.readString(path);
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file: " + path, e);
            }
        }
        public Character peek() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return content.charAt(index);
        }

        public boolean hasNext() {
            return content.length() > index;
        }

        public Character next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return content.charAt(index++);
        }
    }

    private SemanticAnalyzer analyzer;

    @BeforeEach
    void setUp() throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("text")).toURI());
        CodeRepository repo = new CodeRepository(path);
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
        Iterator<SemanticallyCheckable> checkableNodesIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        analyzer = new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
    }

    @Test
    void testImplementsInterface() {
        Assertions.assertInstanceOf(SemanticInterface.class, analyzer);
    }

    @Test
    void testAnalyzeInvalidBinary() {
        AdditionNode additionNode = new AdditionNode();
        LiteralNode literalNode = new LiteralNode("10");
        LiteralNode literalNode2 = new LiteralNode("Pizza");
        additionNode.setLeftChild(literalNode);
        additionNode.setRightChild(literalNode2);
        boolean result = analyzer.isSemanticallyValid(additionNode);
        Assertions.assertFalse(result);
    }

    @Test
    void testAnalyzeInvalidProgram() {
        LetStatementNode letNode = new LetStatementNode();
        IdentifierNode identifierNode = new IdentifierNode("x");
        AscriptionNode ascriptionNode = new AscriptionNode();
        LiteralNode literalNode = new LiteralNode("10");
        letNode.setAscription(ascriptionNode);
        ascriptionNode.setIdentifier(identifierNode);
        letNode.setExpression(literalNode);
        boolean result = analyzer.isSemanticallyValid(letNode);
        Assertions.assertFalse(result);
    }

    @Test
    void testAnalyzePrint() {
        PrintStatementNode printStatementNode = new PrintStatementNode();
        ExpressionNode expressionNode = new LiteralNode("10");
        printStatementNode.setExpression(expressionNode);
        boolean result = analyzer.isSemanticallyValid(printStatementNode);
        Assertions.assertTrue(result);
    }


    @Test
    void testAnalyzeInvalidPrint() {
        PrintStatementNode printStatementNode = new PrintStatementNode();
        ExpressionNode expressionNode = new IdentifierNode("10");
        printStatementNode.setExpression(expressionNode);
        boolean result = analyzer.isSemanticallyValid(printStatementNode);
        Assertions.assertFalse(result);
    }

    @Test
    void testAnalyzeValidBinary() {
        AdditionNode printStatementNode = new AdditionNode();
        LiteralNode literalNode = new LiteralNode("10");
        LiteralNode literalNode2 = new LiteralNode("2");
        printStatementNode.setLeftChild(literalNode);
        printStatementNode.setRightChild(literalNode2);
        boolean result = analyzer.isSemanticallyValid(printStatementNode);
        Assertions.assertTrue(result);
    }

    @Test
    void testAnalyzeValidBinaryCheck() {
        AdditionNode printStatementNode = new AdditionNode();
        LiteralNode literalNode = new LiteralNode("10");
        LiteralNode literalNode2 = new LiteralNode("2");
        printStatementNode.setLeftChild(literalNode);
        printStatementNode.setRightChild(literalNode2);
        SemanticRulesChecker semanticRulesChecker = new SemanticRulesChecker();
        Result<String> result = semanticRulesChecker.check(printStatementNode);
        Assertions.assertEquals("AST passed all semantic rules.",result.result());
    }

    @Test
    void testAnalyzeValiCheck() {
        LetStatementNode letStatementNode = new LetStatementNode();
        LiteralNode literalNode = new LiteralNode("\"Pepe\"");
        IdentifierNode id = new IdentifierNode("\"x\"");
        AscriptionNode ascriptionNode = new AscriptionNode();
        TypeNode type = new TypeNode("String");
        letStatementNode.setAscription(ascriptionNode);
        letStatementNode.setExpression(literalNode);
        ascriptionNode.setIdentifier(id);
        ascriptionNode.setType(type);
        SemanticRulesChecker semanticRulesChecker = new SemanticRulesChecker();
        Result<String> result = semanticRulesChecker.check(letStatementNode);
        Assertions.assertEquals("AST passed all semantic rules.",result.result());
    }

    //Si se ejecutan todos los test funciona, si es individual falla
    @Test
    void testAnalyzeValidIdentifierCheck() {
        IdentifierNode id = new IdentifierNode("\"x\"");
        SemanticRulesChecker semanticRulesChecker = new SemanticRulesChecker();
        Result<String> result = semanticRulesChecker.check(id);
        Assertions.assertEquals("AST passed all semantic rules.",result.result());
        Assertions.assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void testAnalyzeValidLiteralCheck() {
        LiteralNode literalNode = new LiteralNode("10");
        SemanticRulesChecker semanticRulesChecker = new SemanticRulesChecker();
        Result<String> result = semanticRulesChecker.check(literalNode);
        Assertions.assertEquals("AST passed all semantic rules.",result.result());
        Assertions.assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void testOperationsSemanticAnalyzer() {
        Assertions.assertTrue(analyzer.hasNext());
        Assertions.assertInstanceOf(LetStatementNode.class,analyzer.next());
        Assertions.assertInstanceOf(PrintStatementNode.class,analyzer.peek());
        Assertions.assertInstanceOf(PrintStatementNode.class,analyzer.next());
     }

}
