package com.ingsis.nodes.factories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeFactoryInterfaceTest {
    @Test
    public void defaultFactoryImplementsNodeFactory() {
        assertTrue(NodeFactory.class.isAssignableFrom(DefaultNodeFactory.class));
    }
}
