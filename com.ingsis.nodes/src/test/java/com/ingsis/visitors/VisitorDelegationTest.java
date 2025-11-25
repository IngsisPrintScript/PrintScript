/*
 * My Project
 */

package com.ingsis.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.types.Types;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VisitorDelegationTest {
    private static final Visitor SIMPLE_VISITOR =
            new Visitor() {
                @Override
                public Result<String> visit(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("if");
                }

                @Override
                public Result<String> visit(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("decl");
                }

                @Override
                public Result<String> visit(CallFunctionNode callFunctionNode) {
                    return new CorrectResult<>("call");
                }

                @Override
                public Result<String> visit(BinaryOperatorNode binaryOperatorNode) {
                    return new CorrectResult<>("bin");
                }

                @Override
                public Result<String> visit(TypeAssignationNode typeAssignationNode) {
                    return new CorrectResult<>("typeAssign");
                }

                @Override
                public Result<String> visit(ValueAssignationNode valueAssignationNode) {
                    return new CorrectResult<>("valueAssign");
                }

                @Override
                public Result<String> visit(IdentifierNode identifierNode) {
                    return new CorrectResult<>("id");
                }

                @Override
                public Result<String> visit(LiteralNode literalNode) {
                    return new CorrectResult<>("lit");
                }

                @Override
                public Result<String> visit(TypeNode typeNode) {
                    return new CorrectResult<>("type");
                }
            };

    @Test
    public void nodesDelegateAcceptVisitorToVisitorMethods() {
        // when / then: call smaller helpers to verify delegation
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        LiteralNode lit = new LiteralNode("v", 1, 1);
        TypeNode t = new TypeNode(Types.NUMBER, 1, 1);

        assertIdentifierAndLiteralDelegate(SIMPLE_VISITOR, id, lit);
        assertTypeAndBinaryOperatorDelegate(SIMPLE_VISITOR, t, lit, id);
        assertCallAndAssignationsDelegate(SIMPLE_VISITOR, lit, t);
        assertDeclarationAndIfDelegate(SIMPLE_VISITOR, t, lit);
    }

    private void assertIdentifierAndLiteralDelegate(
            Visitor visitor, IdentifierNode id, LiteralNode lit) {
        Result<String> r1 = id.acceptVisitor(visitor);
        assertTrue(r1.isCorrect());
        assertEquals("id", r1.result());

        Result<String> r2 = lit.acceptVisitor(visitor);
        assertTrue(r2.isCorrect());
        assertEquals("lit", r2.result());
    }

    private void assertTypeAndBinaryOperatorDelegate(
            Visitor visitor, TypeNode t, LiteralNode lit, IdentifierNode id) {
        Result<String> r3 = t.acceptVisitor(visitor);
        assertTrue(r3.isCorrect());
        assertEquals("type", r3.result());

        BinaryOperatorNode bin = new BinaryOperatorNode("+", lit, id, 1, 1);
        Result<String> r4 = bin.acceptVisitor(visitor);
        assertTrue(r4.isCorrect());
        assertEquals("bin", r4.result());
    }

    private void assertCallAndAssignationsDelegate(Visitor visitor, LiteralNode lit, TypeNode t) {
        CallFunctionNode call =
                new CallFunctionNode(new IdentifierNode("f", 1, 1), List.of(lit), 1, 1);
        Result<String> r5 = call.acceptVisitor(visitor);
        assertTrue(r5.isCorrect());
        assertEquals("call", r5.result());

        TypeAssignationNode typeAssign =
                new TypeAssignationNode(new IdentifierNode("a", 1, 1), t, 1, 1);
        Result<String> r6 = typeAssign.acceptVisitor(visitor);
        assertTrue(r6.isCorrect());
        assertEquals("typeAssign", r6.result());

        ValueAssignationNode valueAssign =
                new ValueAssignationNode(new IdentifierNode("b", 1, 1), lit, 1, 1);
        Result<String> r7 = valueAssign.acceptVisitor(visitor);
        assertTrue(r7.isCorrect());
        assertEquals("valueAssign", r7.result());
    }

    private void assertDeclarationAndIfDelegate(Visitor visitor, TypeNode t, LiteralNode lit) {
        TypeAssignationNode typeAssign =
                new TypeAssignationNode(new IdentifierNode("a", 1, 1), t, 1, 1);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(new IdentifierNode("b", 1, 1), lit, 1, 1);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, true, 1, 1);
        Result<String> r8 = decl.acceptVisitor(visitor);
        assertTrue(r8.isCorrect());
        assertEquals("decl", r8.result());

        IfKeywordNode ifn = new IfKeywordNode(lit, List.of(lit), List.of(lit), 1, 1);
        Result<String> r9 = ifn.acceptVisitor(visitor);
        assertTrue(r9.isCorrect());
        assertEquals("if", r9.result());
    }
}
