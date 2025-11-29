/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterIdentifierHandlerTest {
    private ResultFactory resultFactory;
    private FormatterIdentifierHandler handler;

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        handler = new FormatterIdentifierHandler(resultFactory);
    }

    @Test
    void handleWithIdentifierReturnsName() {
        IdentifierNode id = new IdentifierNode("myVar", 1, 1);
        CorrectResult<String> r = (CorrectResult<String>) handler.handle(id);
        assertEquals("myVar", r.result());
    }

    @Test
    void handleWithWrongTypeReturnsIncorrect() {
        LiteralNode lit = new LiteralNode("42", 1, 1);
        IncorrectResult<String> r = (IncorrectResult<String>) handler.handle(lit);
        assertEquals("Incorrect handler.", r.error());
    }
}
