/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OperatorNodeTest {
    @Test
    public void binaryOperatorIsOperatorNode() {
        assertTrue(OperatorNode.class.isAssignableFrom(BinaryOperatorNode.class));
    }
}
