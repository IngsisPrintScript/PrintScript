/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterOperatorHandlerTest {
    private ResultFactory resultFactory;
    private Supplier<NodeEventHandler<ExpressionNode>> leafSupplier;
    private FormatterOperatorHandler handler;

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        leafSupplier =
                () ->
                        node -> {
                            if (node instanceof IdentifierNode id) {
                                return resultFactory.createCorrectResult(id.name());
                            }
                            if (node instanceof LiteralNode lit) {
                                return resultFactory.createCorrectResult(lit.value());
                            }
                            return resultFactory.createIncorrectResult("bad");
                        };
        handler = new FormatterOperatorHandler(resultFactory, leafSupplier);
    }

    @Test
    void handleWithOperatorFormatsBothSides() {
        IdentifierNode left = new IdentifierNode("a", 1, 1);
        LiteralNode right = new LiteralNode("10", 1, 2);
        BinaryOperatorNode op = new BinaryOperatorNode("+", left, right, 1, 1);

        CorrectResult<String> r = (CorrectResult<String>) handler.handle(op);
        assertEquals("a + 10", r.result());
    }

    @Test
    void handleWhenLeftFailsReturnsClonedIncorrect() {
        // supplier that returns incorrect for left
        Supplier<NodeEventHandler<ExpressionNode>> bad =
                () -> n -> resultFactory.createIncorrectResult("left fail");
        FormatterOperatorHandler badHandler = new FormatterOperatorHandler(resultFactory, bad);
        IdentifierNode left = new IdentifierNode("a", 1, 1);
        LiteralNode right = new LiteralNode("10", 1, 2);
        BinaryOperatorNode op = new BinaryOperatorNode("+", left, right, 1, 1);

        IncorrectResult<String> r = (IncorrectResult<String>) badHandler.handle(op);
        assertEquals("left fail", r.error());
    }
}
