/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.identifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class IdentifierPatternCheckerTest {

    @Test
    void returnsCorrectWhenMatches() {
        ResultFactory rf = new DefaultResultFactory();
        IdentifierPatternChecker checker = new IdentifierPatternChecker(rf, "[a-z]+", "LOWER");
        IdentifierNode node = new IdentifierNode("abc", 1, 2);
        Result<String> res = checker.handle(node);
        assertTrue(res.isCorrect());
        assertEquals("Check passed.", res.result());
    }

    @Test
    void returnsIncorrectWithFormattedMessageWhenNotMatching() {
        ResultFactory rf = new DefaultResultFactory();
        IdentifierPatternChecker checker = new IdentifierPatternChecker(rf, "[a-z]+", "LOWER");
        IdentifierNode node = new IdentifierNode("123", 7, 8);
        Result<String> res = checker.handle(node);
        assertFalse(res.isCorrect());
        assertEquals(
                "Identifier: LOWER did not respected 123 on line: 7 and column 8", res.error());
    }
}
