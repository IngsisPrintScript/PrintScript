package com.ingsis.printscript.linter;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.linter.api.AnalyzerConfig;
import com.ingsis.printscript.linter.api.Violation;
import com.ingsis.printscript.linter.rules.NamingRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NamingRuleTest {

    private NamingRule rule;
    private AnalyzerConfig configCamel;
    private AnalyzerConfig configSnake;

    @BeforeEach
    void setUp() {
        rule = new NamingRule();

        configCamel = new AnalyzerConfig(new AnalyzerConfig.Naming(true, AnalyzerConfig.CaseStyle.CAMEL), new AnalyzerConfig.Println(true, true) );

        configSnake = new AnalyzerConfig(new AnalyzerConfig.Naming(true, AnalyzerConfig.CaseStyle.SNAKE), new AnalyzerConfig.Println(true, true));
    }

    @Test
    void testValidCamelCaseIdentifier() {
        IdentifierNode node = new IdentifierNode("validName");
        List<Violation> violations = rule.check(node, configCamel);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidCamelCaseIdentifier() {
        IdentifierNode node = new IdentifierNode("Invalid_name");
        List<Violation> violations = rule.check(node, configCamel);
        rule.check(node);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals("identifier 'invalid_name' must be camel", violations.get(0).message().toLowerCase());
    }

    @Test
    void testValidSnakeCaseIdentifier() {
        IdentifierNode node = new IdentifierNode("valid_name");
        List<Violation> violations = rule.check(node, configSnake);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidSnakeCaseIdentifier() {
        IdentifierNode node = new IdentifierNode("InvalidName");
        List<Violation> violations = rule.check(node, configSnake);
        rule.check(node);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals("identifier 'invalidname' must be snake", violations.get(0).message().toLowerCase());
    }

    @Test
    void testRuleMetadata() {
        Assertions.assertEquals("PS-NAMING", rule.id());
        Assertions.assertTrue(rule.description().contains("Identifiers"));
    }
    @Test
    void testLetStatementWithAscriptionAndIdentifier() {
        LetStatementNode node = new LetStatementNode();
        AscriptionNode as = new AscriptionNode();
        IdentifierNode id = new IdentifierNode("myVar");
        TypeNode type = new TypeNode("String");
        as.setIdentifier(id);
        as.setType(type);
        node.setAscription(as);

        AdditionNode add = new AdditionNode();
        add.setLeftChild(new IdentifierNode("a"));
        add.setRightChild(new IdentifierNode("b"));
        node.setExpression(add);

        rule.check(node, configCamel);
        var result = rule.check(node);

        Assertions.assertTrue(result.isSuccessful());
        List<Violation> violations = rule.check(node, configCamel);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testCheckBinaryExpression() {
        AdditionNode add = new AdditionNode();
        add.setLeftChild(new IdentifierNode("a"));
        add.setRightChild(new IdentifierNode("b"));

        rule.check(add, configCamel);
        var result = rule.check(add);

        Assertions.assertTrue(result.isSuccessful());
        List<Violation> violations = rule.check(add, configCamel);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testCheckLiteralNode() {
        LiteralNode lit = new LiteralNode("42");

        rule.check(lit, configCamel);
        var result = rule.check(lit);

        Assertions.assertTrue(result.isSuccessful());
        List<Violation> violations = rule.check(lit, configCamel);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testCheckPrintStatementDirectly() {
        PrintStatementNode node = new PrintStatementNode();
        node.setExpression(new IdentifierNode("x"));

        rule.check(node, configCamel);
        var result = rule.check(node);

        Assertions.assertTrue(result.isSuccessful());
        List<Violation> violations = rule.check(node, configCamel);
        Assertions.assertTrue(violations.isEmpty());
    }
}
