/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CallFunctionNodeTest {

    private CallFunctionNode callNode;

    private static final Visitor SIMPLE_VISITOR =
            new Visitor() {
                @Override
                public Result<String> visit(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(CallFunctionNode callFunctionNode) {
                    return new CorrectResult<>("visited");
                }

                @Override
                public Result<String> visit(BinaryOperatorNode binaryOperatorNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(TypeAssignationNode typeAssignationNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(ValueAssignationNode valueAssignationNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(IdentifierNode identifierNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(LiteralNode literalNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> visit(TypeNode typeNode) {
                    return new CorrectResult<>("ok");
                }
            };

    private static final Interpreter GOOD_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    return new CorrectResult<>(new Object());
                }
            };

    private static final Interpreter BAD_INTERPRETER =
            new Interpreter() {
                @Override
                public Result<String> interpret(IfKeywordNode ifKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<String> interpret(DeclarationKeywordNode declarationKeywordNode) {
                    return new CorrectResult<>("ok");
                }

                @Override
                public Result<Object> interpret(ExpressionNode expressionNode) {
                    return new IncorrectResult<>("err");
                }
            };

    @BeforeEach
    public void setUp() {
        IdentifierNode id = new IdentifierNode("f", 1, 1);
        LiteralNode arg = new LiteralNode("1", 1, 2);
        callNode = new CallFunctionNode(id, List.of(arg), 1, 3);
    }

    @Test
    public void argumentNodesAreImmutableAndChildrenIncludeIdentifier() {
        assertEquals(1, callNode.argumentNodes().size());
        assertEquals("f", callNode.symbol());
        assertEquals(2, callNode.children().size());
    }

    @Test
    public void isTerminalTrue() {
        assertEquals(Boolean.TRUE, callNode.isTerminalNode());
    }

    @Test
    public void acceptVisitorDelegates() {
        Visitor visitor = SIMPLE_VISITOR;

        Result<String> r = callNode.acceptVisitor(visitor);
        assertEquals("visited", r.result());
    }

    @Test
    public void acceptInterpreterHandlesResults() {
        Interpreter good = GOOD_INTERPRETER;

        Result<String> rGood = callNode.acceptInterpreter(good);
        assertEquals("Interpreted successfully.", rGood.result());

        Interpreter bad = BAD_INTERPRETER;

        Result<String> rBad = callNode.acceptInterpreter(bad);
        assertEquals("err", rBad.error());
    }
}
