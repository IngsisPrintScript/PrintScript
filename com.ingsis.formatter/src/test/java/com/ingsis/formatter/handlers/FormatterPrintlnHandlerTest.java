/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterPrintlnHandlerTest {
    private ResultFactory resultFactory;
    private Supplier<NodeEventHandler<ExpressionNode>> supplier;
    private FormatterPrintlnHandler handler;

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        supplier =
                () ->
                        node -> {
                            if (node instanceof LiteralNode lit) {
                                return resultFactory.createCorrectResult(lit.value());
                            }
                            if (node instanceof IdentifierNode id) {
                                return resultFactory.createCorrectResult(id.name());
                            }
                            return resultFactory.createIncorrectResult("bad");
                        };
        handler = new FormatterPrintlnHandler(2, supplier, resultFactory);
    }

    @Test
    void handlePrintlnAddsNewLinesAndFormatsArgument() {
        IdentifierNode id = new IdentifierNode("println", 1, 1);
        CallFunctionNode call = new CallFunctionNode(id, List.of(new LiteralNode("x", 1, 2)), 1, 1);
        var r = handler.handle(call);
        if (!r.isCorrect()) {
            if (r instanceof IncorrectResult<?> ir) {
                throw new AssertionError("RESULT incorrect: <" + ir.error() + ">");
            } else {
                throw new AssertionError("RESULT not correct: " + r.getClass());
            }
        }
        CorrectResult<String> cr = (CorrectResult<String>) r;
        String out = cr.result();
        String expected =
                "\n\n" + call.symbol() + "( " + call.identifierNode().name() + " )" + " ;\n";
        assertEquals(expected, out);
    }

    @Test
    void handleWrongTypeReturnsIncorrect() {
        LiteralNode lit = new LiteralNode("42", 1, 1);
        IncorrectResult<String> r = (IncorrectResult<String>) handler.handle(lit);
        assertEquals("Incorrect handler.", r.error());
    }
}
