/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.interpreter.visitor.DefaultInterpreterVisitor;
import com.ingsis.interpreter.visitor.expression.strategies.factories.SolutionStrategyFactory;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.visitors.Interpreter;
import java.util.List;
import org.junit.jupiter.api.Test;

class DefaultInterpreterVisitorFactoryTest {
    @Test
    void createsInterpreter() {
        SolutionStrategyFactory sf = () -> null;
        DefaultInterpreterVisitorFactory factory =
                new DefaultInterpreterVisitorFactory(sf, new DefaultResultFactory());
        Interpreter interpreter = factory.createDefaultInterpreter(DefaultRuntime.getInstance());
        assertNotNull(interpreter);
        assertTrue(interpreter instanceof DefaultInterpreterVisitor);
    }

    @Test
    void factoryProducedInterpreterCanInterpretIfNode() {
        SolutionStrategyFactory sf = () -> (visitor, node) -> new CorrectResult<>("true");
        DefaultInterpreterVisitorFactory factory =
                new DefaultInterpreterVisitorFactory(sf, new DefaultResultFactory());
        Interpreter interpreter = factory.createDefaultInterpreter(DefaultRuntime.getInstance());

        LiteralNode cond = new LiteralNode("true", 0, 0);
        IfKeywordNode ifNode = new IfKeywordNode(cond, List.of(cond), 0, 0);
        Result<String> res = interpreter.interpret(ifNode);
        // since strategy returns "true", result should be the children interpretation result
        assertNotNull(res);
    }
}
