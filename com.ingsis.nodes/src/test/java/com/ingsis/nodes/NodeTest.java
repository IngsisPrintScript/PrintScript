package com.ingsis.nodes;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {

    private Node testNode;

    @BeforeEach
    public void setUp() {
        testNode = new Node() {
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
                return new CorrectResult<>("visited");
            }
        };
    }

    @Test
    public void lineAndColumnAreReturned() {
        assertEquals(Integer.valueOf(10), testNode.line());
        assertEquals(Integer.valueOf(20), testNode.column());
    }
}
