/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NodeFactoryInterfaceTest {
    @Test
    public void defaultFactoryImplementsNodeFactory() {
        assertTrue(NodeFactory.class.isAssignableFrom(DefaultNodeFactory.class));
    }
}
