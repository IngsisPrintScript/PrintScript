/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import org.junit.jupiter.api.Test;

public class InterpreterDelegationTest {
    private static final Interpreter SIMPLE_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ifI");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("declI");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    if (expressionNode instanceof IdentifierNode) {
                        return new IncorrectResult<>("bad id");
                    }
                    if (expressionNode instanceof LiteralNode) {
                        return new CorrectResult<>("litVal");
                    }
                    if (expressionNode instanceof BinaryOperatorNode) {
                        return new CorrectResult<>(42);
                    }
                    return new CorrectResult<>(null);
                }
            };

    @Test
    public void expressionsDelegateAcceptInterpreterAndHandleCorrectAndIncorrect() {
        LiteralNode lit = new LiteralNode("v", 1, 1);
        Result<String> r1 = lit.acceptInterpreter(SIMPLE_INTERPRETER);
        assertTrue(r1.isCorrect());
        assertEquals("Interpreted successfully.", r1.result());

        IdentifierNode id = new IdentifierNode("z", 1, 1);
        Result<String> r2 = id.acceptInterpreter(SIMPLE_INTERPRETER);
        assertFalse(r2.isCorrect());

        BinaryOperatorNode bin = new BinaryOperatorNode("*", lit, id, 1, 1);
        Result<String> r3 = bin.acceptInterpreter(SIMPLE_INTERPRETER);
        assertTrue(r3.isCorrect());
    }

    @Test
    public void keywordsDelegateAcceptInterpreterAndHandleCorrectAndIncorrect() {
        // Build declaration using simple assignation nodes
        TypeAssignationNode tnode =
                new TypeAssignationNode(
                        new IdentifierNode("a", 1, 1), new TypeNode(Types.NUMBER, 1, 1), 1, 1);
        ValueAssignationNode vnode =
                new ValueAssignationNode(
                        new IdentifierNode("b", 1, 1), new LiteralNode("x", 1, 1), 1, 1);
        DeclarationKeywordNode decl = new DeclarationKeywordNode(tnode, vnode, true, 1, 1);
        Result<String> r4 = decl.acceptInterpreter(SIMPLE_INTERPRETER);
        assertTrue(r4.isCorrect());

        IfKeywordNode ifn =
                new IfKeywordNode(
                        new LiteralNode("x", 1, 1),
                        List.of(new LiteralNode("x", 1, 1)),
                        List.of(new LiteralNode("x", 1, 1)),
                        1,
                        1);
        Result<String> r6 = ifn.acceptInterpreter(SIMPLE_INTERPRETER);
        assertTrue(r6.isCorrect());
    }
}
