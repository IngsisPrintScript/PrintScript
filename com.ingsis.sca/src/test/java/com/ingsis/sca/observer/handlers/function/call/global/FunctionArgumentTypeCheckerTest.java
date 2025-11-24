/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.function.call.global;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class FunctionArgumentTypeCheckerTest {

    @Test
    void returnsCorrectWhenFunctionNameDiffers() {
        ResultFactory rf = new DefaultResultFactory();
        FunctionArgumentTypeChecker checker = new FunctionArgumentTypeChecker(rf, "f1", 0);

        IdentifierNode id = new IdentifierNode("f2", 1, 1);
        CallFunctionNode node = new CallFunctionNode(id, List.of(), 1, 1);

        Result<String> res = checker.handle(node);
        assertTrue(res.isCorrect());
    }

    @Test
    void returnsIncorrectWhenArgIsNotIdentifierOrLiteral() {
        ResultFactory rf = new DefaultResultFactory();
        String name = "myFun";
        FunctionArgumentTypeChecker checker = new FunctionArgumentTypeChecker(rf, name, 1);

        IdentifierNode id = new IdentifierNode(name, 2, 3);
        CallFunctionNode badArg =
                new CallFunctionNode(new IdentifierNode("g", 5, 6), List.of(), 9, 11);
        CallFunctionNode node =
                new CallFunctionNode(id, List.of(new IdentifierNode("x", 1, 1), badArg), 2, 3);

        Result<String> res = checker.handle(node);
        assertFalse(res.isCorrect());
        String msg = res.error();
        assertTrue(msg.contains("myFun"));
        assertTrue(msg.contains("argument number: 1"));
        assertTrue(msg.contains(badArg.getClass().toString()));
        assertTrue(msg.contains("9"));
        assertTrue(msg.contains("11"));
    }
}
