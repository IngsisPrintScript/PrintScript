/*
 * My Project
 */

package com.ingsis.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.types.Types;
import java.util.List;
import org.junit.jupiter.api.Test;

public class InterpreterDelegationTest {

    @Test
    public void nodes_delegateAcceptInterpreter_and_handleCorrectAndIncorrect() {
        Interpreter interpreter =
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

        LiteralNode lit = new LiteralNode("v", 1, 1);
        Result<String> r1 = lit.acceptInterpreter(interpreter);
        assertTrue(r1.isCorrect());
        assertEquals("Interpreted successfully.", r1.result());

        IdentifierNode id = new IdentifierNode("z", 1, 1);
        Result<String> r2 = id.acceptInterpreter(interpreter);
        assertFalse(r2.isCorrect());

        BinaryOperatorNode bin = new BinaryOperatorNode("*", lit, id, 1, 1);
        Result<String> r3 = bin.acceptInterpreter(interpreter);
        assertTrue(r3.isCorrect());

        // Build declaration using simple assignation nodes
        TypeAssignationNode tnode =
                new TypeAssignationNode(
                        new IdentifierNode("a", 1, 1), new TypeNode(Types.NUMBER, 1, 1), 1, 1);
        ValueAssignationNode vnode =
                new ValueAssignationNode(new IdentifierNode("b", 1, 1), lit, 1, 1);
        DeclarationKeywordNode decl = new DeclarationKeywordNode(tnode, vnode, true, 1, 1);
        Result<String> r4 = decl.acceptInterpreter(interpreter);
        assertTrue(r4.isCorrect());

        IfKeywordNode ifn = new IfKeywordNode(lit, List.of(lit), List.of(lit), 1, 1);
        Result<String> r6 = ifn.acceptInterpreter(interpreter);
        assertTrue(r6.isCorrect());
    }
}
