/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterLiteralHandlerTest {
    private ResultFactory resultFactory;
    private FormatterLiteralHandler handler;

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        handler = new FormatterLiteralHandler(resultFactory);
    }

    @Test
    void handleWithLiteralReturnsValue() {
        LiteralNode lit = new LiteralNode("42", 1, 1);
        CorrectResult<String> r = (CorrectResult<String>) handler.handle(lit);
        assertEquals("42", r.result());
    }

    @Test
    void handleWithWrongTypeReturnsIncorrect() {
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        IncorrectResult<String> r = (IncorrectResult<String>) handler.handle(id);
        assertEquals("Incorrect handler.", r.error());
    }
}
