/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CheckerDelegationTest {
    private static final Checker SIMPLE_CHECKER =
            new Checker() {
                @Override
                public Result<String> check(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ifC");
                }

                @Override
                public Result<String> check(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("declC");
                }

                @Override
                public Result<String> check(ExpressionNode expressionNode) {
                    return new CorrectResult<>("exprC");
                }
            };

    @Test
    public void expressionsDelegateAcceptCheckerToCheckerMethods() {
        IdentifierNode id = new IdentifierNode("y", 1, 1);
        Result<String> r1 = id.acceptChecker(SIMPLE_CHECKER);
        assertTrue(r1.isCorrect());
        assertEquals("exprC", r1.result());

        LiteralNode lit = new LiteralNode("vv", 1, 1);
        Result<String> r2 = lit.acceptChecker(SIMPLE_CHECKER);
        assertTrue(r2.isCorrect());
        assertEquals("exprC", r2.result());

        BinaryOperatorNode bin = new BinaryOperatorNode("+", lit, id, 1, 1);
        Result<String> r3 = bin.acceptChecker(SIMPLE_CHECKER);
        assertTrue(r3.isCorrect());
        assertEquals("exprC", r3.result());
    }

    @Test
    public void keywordsDelegateAcceptCheckerToCheckerMethods() {
        TypeAssignationNode t =
                new TypeAssignationNode(
                        new IdentifierNode("a", 1, 1), new TypeNode(Types.NUMBER, 1, 1), 1, 1);
        ValueAssignationNode v =
                new ValueAssignationNode(
                        new IdentifierNode("b", 1, 1), new LiteralNode("x", 1, 1), 1, 1);
        DeclarationKeywordNode decl = new DeclarationKeywordNode(t, v, true, 1, 1);
        Result<String> r4 = decl.acceptChecker(SIMPLE_CHECKER);
        assertTrue(r4.isCorrect());
        assertEquals("declC", r4.result());

        IfKeywordNode ifn =
                new IfKeywordNode(
                        new LiteralNode("x", 1, 1),
                        List.of(new LiteralNode("x", 1, 1)),
                        List.of(new LiteralNode("x", 1, 1)),
                        1,
                        1);
        Result<String> r5 = ifn.acceptChecker(SIMPLE_CHECKER);
        assertTrue(r5.isCorrect());
        assertEquals("ifC", r5.result());
    }
}
