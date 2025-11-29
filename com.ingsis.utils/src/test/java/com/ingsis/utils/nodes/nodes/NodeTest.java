/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeTest {

    private Node testNode;
    private final DefaultResultFactory resultFactory = new DefaultResultFactory();

    @BeforeEach
    public void setUp() {
        testNode =
                new Node() {
                    @Override
                    public Integer line() {
                        return 10;
                    }

                    @Override
                    public Integer column() {
                        return 20;
                    }

                    @Override
                    public Result<String> acceptVisitor(Visitor visitor) {
                        return resultFactory.createCorrectResult("visited");
                    }
                };
    }

    @Test
    public void lineAndColumnAreReturned() {
        assertEquals(Integer.valueOf(10), testNode.line());
        assertEquals(Integer.valueOf(20), testNode.column());
    }
}
