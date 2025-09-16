package com.ingsis.printscript.linter;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.AssignationNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.linter.api.Rule;
import com.ingsis.printscript.linter.api.Violation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class LinterTest {

    @Test
    void testCreateDefaultRules() {
        List<Rule> rules = AnalyzerFactory.defaultRunner().getRules();
        Assertions.assertNotNull(rules);
        Assertions.assertFalse(rules.isEmpty());
    }

    @Test
    void testRunWithNoRulesReturnsEmptyViolations() {
        LetStatementNode letNode = new LetStatementNode();
        IdentifierNode identifierNode = new IdentifierNode("x");
        AscriptionNode ascriptionNode = new AscriptionNode();
        LiteralNode literalNode = new LiteralNode("10");
        letNode.setAscription(ascriptionNode);
        ascriptionNode.setIdentifier(identifierNode);
        letNode.setExpression(literalNode);
        AnalyzerRunner runner = new AnalyzerRunner(Collections.emptyList());
        List<Violation> violations = runner.analyze(letNode, null);
        Assertions.assertTrue(violations.isEmpty());
    }
}
