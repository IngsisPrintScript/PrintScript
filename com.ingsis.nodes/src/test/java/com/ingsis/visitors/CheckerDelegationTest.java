package com.ingsis.visitors;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckerDelegationTest {

    @Test
    public void nodes_delegateAcceptChecker_toCheckerMethods() {
        Checker checker = new Checker() {
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

        IdentifierNode id = new IdentifierNode("y", 1,1);
        Result<String> r1 = id.acceptChecker(checker);
        assertTrue(r1.isCorrect());
        assertEquals("exprC", r1.result());

        LiteralNode lit = new LiteralNode("vv",1,1);
        Result<String> r2 = lit.acceptChecker(checker);
        assertTrue(r2.isCorrect());
        assertEquals("exprC", r2.result());

        BinaryOperatorNode bin = new BinaryOperatorNode("+", lit, id, 1, 1);
        Result<String> r3 = bin.acceptChecker(checker);
        assertTrue(r3.isCorrect());
        assertEquals("exprC", r3.result());

        // build a DeclarationKeywordNode using auxiliary nodes but do not call accept on them
        TypeAssignationNode t = new TypeAssignationNode(new IdentifierNode("a",1,1), new com.ingsis.nodes.type.TypeNode(com.ingsis.types.Types.NUMBER,1,1),1,1);
        ValueAssignationNode v = new ValueAssignationNode(new IdentifierNode("b",1,1), lit,1,1);
        DeclarationKeywordNode decl = new DeclarationKeywordNode(t, v, true,1,1);
        Result<String> r4 = decl.acceptChecker(checker);
        assertTrue(r4.isCorrect());
        assertEquals("declC", r4.result());

        IfKeywordNode ifn = new IfKeywordNode(lit, List.of(lit), List.of(lit),1,1);
        Result<String> r5 = ifn.acceptChecker(checker);
        assertTrue(r5.isCorrect());
        assertEquals("ifC", r5.result());
    }
}
