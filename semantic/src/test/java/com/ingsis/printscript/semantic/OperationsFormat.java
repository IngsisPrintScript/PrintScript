package com.ingsis.printscript.semantic;

import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.enforcers.SemanticRulesChecker;
import com.ingsis.printscript.semantic.rules.operations.BinaryOperationFormatSemanticSemanticRule;
import com.ingsis.printscript.semantic.rules.operations.OperationFormatSemanticRule;
import com.ingsis.printscript.semantic.rules.type.ExpressionTypeGetter;
import com.ingsis.printscript.semantic.rules.type.ExpressionTypeGetterInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationsFormat {

    private final BinaryOperationFormatSemanticSemanticRule binaryOperationFormatSemanticRule = new BinaryOperationFormatSemanticSemanticRule();
    private final OperationFormatSemanticRule operationsFormat = new OperationFormatSemanticRule();
    @Test
    void testNonBinaryExpression() {
        LiteralNode node = new LiteralNode("10");

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(node);

        Assertions.assertFalse(result.isSuccessful());
        Assertions.assertEquals("This rule does not apply to the received node.", result.errorMessage());
    }

    @Test
    void testLeftChildError() {
        AdditionNode binary = new AdditionNode();

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(binary);

        Assertions.assertFalse(result.isSuccessful());
        Assertions.assertEquals("It has no left child.", result.errorMessage());
    }

    @Test
    void testLiteralsSameType() {
        LiteralNode left = new LiteralNode("2");
        LiteralNode right = new LiteralNode("10");

        AdditionNode addition = new AdditionNode();
        addition.setLeftChild(left);
        addition.setRightChild(right);

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(addition);

        Assertions.assertTrue(result.isSuccessful());
        Assertions.assertEquals("This node passes the check.", result.result());
    }

    @Test
    void testLiteralsDifferentType() {
        AdditionNode addition = new AdditionNode();
        LiteralNode left = new LiteralNode("\"2\"");
        LiteralNode right = new LiteralNode("10");
        addition.setLeftChild(left);
        addition.setRightChild(right);

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(addition);

        Assertions.assertFalse(result.isSuccessful());
        Assertions.assertEquals("This node does not pass the check.", result.errorMessage());
    }

    @Test
    void testIdentifiersSameType() {
        IdentifierNode left = new IdentifierNode("name");
        IdentifierNode right = new IdentifierNode("pepe");

        AdditionNode binary = new AdditionNode();
        binary.setLeftChild(left);
        binary.setRightChild(right);

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(binary);

        Assertions.assertTrue(result.isSuccessful());
    }

    @Test
    void testIdentifiersDifferentType() {
        IdentifierNode left = new IdentifierNode("name");
        LiteralNode right = new LiteralNode("10");

        AdditionNode binary = new AdditionNode();
        binary.setLeftChild(left);
        binary.setRightChild(right);

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(binary);

        Assertions.assertFalse(result.isSuccessful());
    }

    @Test
    void testAnotherNode() throws StackOverflowError {
        AdditionNode additionNode = new AdditionNode();
        LiteralNode right = new LiteralNode("2");
        LiteralNode mid = new LiteralNode("3");
        additionNode.setLeftChild(right);
        additionNode.setRightChild(mid);
        AdditionNode add = new AdditionNode();
        LiteralNode left = new LiteralNode("1");
        add.setLeftChild(left);
        add.setRightChild(additionNode);

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(add);
        Assertions.assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void testAnotherRightNode() throws StackOverflowError {
        AdditionNode additionNode = new AdditionNode();
        LiteralNode right = new LiteralNode("2");
        LiteralNode mid = new LiteralNode("3");
        additionNode.setLeftChild(right);
        additionNode.setRightChild(mid);
        AdditionNode add = new AdditionNode();
        LiteralNode left = new LiteralNode("1");
        add.setRightChild(left);
        add.setLeftChild(additionNode);

        Result<String> result = binaryOperationFormatSemanticRule.checkRules(add);
        Assertions.assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void testOperationFormat() throws StackOverflowError {
        AdditionNode additionNode = new AdditionNode();
        LiteralNode right = new LiteralNode("2");
        LiteralNode mid = new LiteralNode("3");
        additionNode.setLeftChild(right);
        additionNode.setRightChild(mid);
        AdditionNode add = new AdditionNode();
        LiteralNode left = new LiteralNode("1");
        add.setRightChild(left);
        add.setLeftChild(additionNode);

        Result<String> result = operationsFormat.checkRules(add);
        Assertions.assertInstanceOf(CorrectResult.class, result);
    }

    @Test
    void testOperationFormatMatch() throws StackOverflowError {
        AdditionNode additionNode = new AdditionNode();
        LiteralNode right = new LiteralNode("2");
        LiteralNode mid = new LiteralNode("3");
        additionNode.setLeftChild(right);
        additionNode.setRightChild(mid);
        AdditionNode add = new AdditionNode();
        LiteralNode left = new LiteralNode("1");
        add.setRightChild(left);
        add.setLeftChild(additionNode);

        boolean result = operationsFormat.match(add);
        Assertions.assertTrue(result);
    }


}
