package com.ingsis.nodes.expression.function;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GlobalFunctionBodyCheckerTest {

    @Test
    public void acceptChecker_delegatesToChecker_andReturnsResult() {
        // given
        GlobalFunctionBody gf = new GlobalFunctionBody(List.of("a"), (Function<Object[], Object>) args -> null, 1, 2);

        Checker checker = new Checker() {
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
