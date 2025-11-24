package com.ingsis.visitors;

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
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisitorDelegationTest {

    @Test
    public void nodes_delegateAcceptVisitor_toVisitorMethods() {
        // given: a visitor that returns a distinct correct result per node type
        Visitor visitor = new Visitor() {
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

        // when / then: call acceptVisitor on various nodes and verify delegation
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        Result<String> r1 = id.acceptVisitor(visitor);
        assertTrue(r1.isCorrect());
        assertEquals("id", r1.result());

        LiteralNode lit = new LiteralNode("v", 1, 1);
        Result<String> r2 = lit.acceptVisitor(visitor);
        assertTrue(r2.isCorrect());
        assertEquals("lit", r2.result());

        TypeNode t = new TypeNode(Types.NUMBER, 1, 1);
        Result<String> r3 = t.acceptVisitor(visitor);
        assertTrue(r3.isCorrect());
        assertEquals("type", r3.result());

        BinaryOperatorNode bin = new BinaryOperatorNode("+", lit, id, 1, 1);
        Result<String> r4 = bin.acceptVisitor(visitor);
        assertTrue(r4.isCorrect());
        assertEquals("bin", r4.result());

        CallFunctionNode call = new CallFunctionNode(new IdentifierNode("f", 1, 1), List.of(lit), 1, 1);
        Result<String> r5 = call.acceptVisitor(visitor);
        assertTrue(r5.isCorrect());
        assertEquals("call", r5.result());

        // Declaration and If require more structure but we can create minimal instances
        TypeAssignationNode typeAssign = new TypeAssignationNode(new IdentifierNode("a",1,1), t,1,1);
        Result<String> r6 = typeAssign.acceptVisitor(visitor);
        assertTrue(r6.isCorrect());
        assertEquals("typeAssign", r6.result());

        ValueAssignationNode valueAssign = new ValueAssignationNode(new IdentifierNode("b",1,1), lit,1,1);
        Result<String> r7 = valueAssign.acceptVisitor(visitor);
        assertTrue(r7.isCorrect());
        assertEquals("valueAssign", r7.result());

        DeclarationKeywordNode decl = new DeclarationKeywordNode(typeAssign, valueAssign, true, 1,1);
        Result<String> r8 = decl.acceptVisitor(visitor);
        assertTrue(r8.isCorrect());
        assertEquals("decl", r8.result());

        IfKeywordNode ifn = new IfKeywordNode(lit, List.of(lit), List.of(lit), 1,1);
        Result<String> r9 = ifn.acceptVisitor(visitor);
        assertTrue(r9.isCorrect());
        assertEquals("if", r9.result());
    }
}
