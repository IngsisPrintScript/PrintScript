package com.ingsis.printscript.semantic;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.environment.Environment;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.enforcers.CorrectTypeAssignationEnforcer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CorrectTypeAssignationEnforcerTest {

    private CorrectTypeAssignationEnforcer enforcer;

    @BeforeEach
    void setUp() {
        enforcer = new CorrectTypeAssignationEnforcer();
    }

    @Test
    void testCorrectAssignment() {
        LetStatementNode letNode = new LetStatementNode();
        IdentifierNode identifierNode = new IdentifierNode("x");
        AscriptionNode ascriptionNode = new AscriptionNode();
        TypeNode typeNode = new TypeNode("Number");
        LiteralNode literalNode = new LiteralNode("10");
        letNode.setAscription(ascriptionNode);
        ascriptionNode.setIdentifier(identifierNode);
        ascriptionNode.setType(typeNode);
        letNode.setExpression(literalNode);
        Result<String> result = enforcer.check(letNode);
        Assertions.assertTrue(result.isSuccessful());
    }

    @Test
    void testIncorrectAssignment() {
        LetStatementNode letNode = new LetStatementNode();
        IdentifierNode identifierNode = new IdentifierNode("x");
        AscriptionNode ascriptionNode = new AscriptionNode();
        TypeNode typeNode = new TypeNode("String");
        LiteralNode literalNode = new LiteralNode("10");
        letNode.setAscription(ascriptionNode);
        ascriptionNode.setIdentifier(identifierNode);
        ascriptionNode.setType(typeNode);
        letNode.setExpression(literalNode);
        Result<String> result = enforcer.check(letNode);
        Assertions.assertFalse(result.isSuccessful());
    }


    @Test
    void checkRules_withIdentifierNodeAndMatchingType_returnsCorrectResult() {
        String varName = "x";
        Environment.getInstance().putIdType(varName, "int");

        IdentifierNode node = new IdentifierNode(varName);

        Result<String> result = enforcer.check(node);

        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertTrue(result.isSuccessful());
        Assertions.assertEquals("Type is equal to the expected type", result.result());
    }

    @Test
    void checkRules_withIdentifierNodeAndDifferentType_returnsIncorrectResult() {
        String varName = "y";
        Environment.getInstance().putIdType(varName, "string");

        IdentifierNode node = new IdentifierNode(varName);

        Result<String> result = enforcer.check(node);

        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertTrue(result.isSuccessful());
    }

    @Test
    void checkRules_withLiteralNode_returnsIncorrectResult() {
        LiteralNode node = new LiteralNode("42");

        Result<String> result = enforcer.check(node);

        Assertions.assertInstanceOf(CorrectResult.class, result);
        Assertions.assertTrue(result.isSuccessful());
    }
}
