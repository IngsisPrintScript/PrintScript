/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

public class GlobalFunctionBodyCheckerTest {

    @Test
    public void acceptCheckerDelegatesToCheckerAndReturnsResult() {
        // given
        GlobalFunctionBody gf =
                new GlobalFunctionBody(
                        List.of("a"), (Function<Object[], Object>) args -> null, 1, 2);

        Checker checker =
                new Checker() {
                    @Override
                    public Result<String> check(IfKeywordNode ifKeywordNode) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Result<String> check(DeclarationKeywordNode declarationKeywordNode) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Result<String> check(ExpressionNode expressionNode) {
                        // ensure the node passed is the same instance
                        assertSame(gf, expressionNode);
                        return new CorrectResult<>("checked");
                    }
                };

        // when
        Result<String> result = gf.acceptChecker(checker);

        // then
        assertTrue(result.isCorrect());
        assertEquals("checked", result.result());
    }
}
