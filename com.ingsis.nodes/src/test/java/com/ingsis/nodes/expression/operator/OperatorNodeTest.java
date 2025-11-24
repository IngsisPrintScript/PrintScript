package com.ingsis.nodes.expression.operator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperatorNodeTest {
    @Test
    public void binaryOperatorIsOperatorNode() {
        assertTrue(OperatorNode.class.isAssignableFrom(BinaryOperatorNode.class));
    }
}
