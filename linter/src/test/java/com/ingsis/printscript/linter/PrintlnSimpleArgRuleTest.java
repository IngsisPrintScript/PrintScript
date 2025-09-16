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
import com.ingsis.printscript.linter.rules.PrintlnSimpleArgRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PrintlnSimpleArgRuleTest {

    private PrintlnSimpleArgRule rule;
    private AnalyzerConfig enabledConfig;
    private AnalyzerConfig disabledConfig;

    @BeforeEach
    void setUp() {
        rule = new PrintlnSimpleArgRule();

       enabledConfig = new AnalyzerConfig(
                new AnalyzerConfig.Naming(true, AnalyzerConfig.CaseStyle.CAMEL),
                new AnalyzerConfig.Println(true, true)
        );

        disabledConfig = new AnalyzerConfig(
                new AnalyzerConfig.Naming(true, AnalyzerConfig.CaseStyle.CAMEL),
                new AnalyzerConfig.Println(false, false)
        );
    }

    @Test
    void testRuleMetadata() {
        Assertions.assertEquals("PS-PRINTLN-ARG", rule.id());
        Assertions.assertTrue(rule.description().contains("println"));
    }

    @Test
    void testValidPrintlnWithIdentifier() {
        PrintStatementNode node = new PrintStatementNode();
        IdentifierNode node2 = new IdentifierNode("x");
        node.setExpression(node2);
        rule.check(node, enabledConfig);
        var result = rule.check(node);
        List<Violation> violations = rule.check(node, enabledConfig);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testValidPrintlnWithLiteral() {
        PrintStatementNode node = new PrintStatementNode();
        LiteralNode lit = new LiteralNode("42");
        node.setExpression(lit);
        rule.check(node, enabledConfig);
        var result = rule.check(node);
        List<Violation> violations = rule.check(node, enabledConfig);
        Assertions.assertTrue(violations.isEmpty());
    }

//    @Test
//    void testInvalidPrintlnWithBinaryExpression() {
//        PrintStatementNode node = new PrintStatementNode();
//        AdditionNode add = new AdditionNode();
//        add.setLeftChild(new IdentifierNode("a"));
//        add.setRightChild(new IdentifierNode("b"));
//        node.setExpression(add);
//        rule.check(node, enabledConfig);
//        rule.check(node);
//        List<Violation> violations = rule.check(node, enabledConfig);
//
//        Assertions.assertFalse(violations.isEmpty());
//        Assertions.assertTrue(violations.get(0).message().contains("println argument must be"));
//    }

    @Test
    void testDisabledRuleDoesNotAddViolations() {
        PrintStatementNode node = new PrintStatementNode();
        AdditionNode add = new AdditionNode();
        add.setLeftChild(new IdentifierNode("a"));
        add.setRightChild(new IdentifierNode("b"));
        node.setExpression(add);
        rule.check(node, disabledConfig);
        rule.check(node);
        List<Violation> violations = rule.check(node, disabledConfig);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testLet() {
        LetStatementNode node = new LetStatementNode();
        AscriptionNode as = new AscriptionNode();
        IdentifierNode id = new IdentifierNode("x");
        TypeNode type = new TypeNode("String");
        node.setAscription(as);
        as.setIdentifier(id);
        as.setType(type);
        AdditionNode add = new AdditionNode();
        add.setLeftChild(new IdentifierNode("a"));
        add.setRightChild(new IdentifierNode("b"));
        node.setExpression(add);
        rule.check(node, disabledConfig);
        rule.check(node);
        List<Violation> violations = rule.check(node, disabledConfig);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testIdentifier() {
        IdentifierNode id = new IdentifierNode("x");
        rule.check(id, disabledConfig);
        rule.check(id);
        List<Violation> violations = rule.check(id, disabledConfig);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testLiteral() {
        LiteralNode id = new LiteralNode("x");
        rule.check(id, disabledConfig);
        rule.check(id);
        List<Violation> violations = rule.check(id, disabledConfig);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testExpression() {
        AdditionNode add = new AdditionNode();
        add.setLeftChild(new IdentifierNode("a"));
        add.setRightChild(new IdentifierNode("b"));
        rule.check(add, disabledConfig);
        rule.check(add);
        List<Violation> violations = rule.check(add, disabledConfig);

        Assertions.assertTrue(violations.isEmpty());
    }

}
