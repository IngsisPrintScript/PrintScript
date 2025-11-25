/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import org.junit.jupiter.api.Test;

class DeclarationHandlerTest {

    static class DummyExpression implements com.ingsis.nodes.expression.ExpressionNode {
        @Override
        public java.util.List<com.ingsis.nodes.expression.ExpressionNode> children() {
            return java.util.List.of();
        }

        @Override
        public Boolean isTerminalNode() {
            return true;
        }

        @Override
        public String symbol() {
            return "";
        }

        @Override
        public Integer line() {
            return 1;
        }

        @Override
        public Integer column() {
            return 1;
        }

        @Override
        public com.ingsis.result.Result<String> acceptChecker(com.ingsis.visitors.Checker checker) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }

        @Override
        public com.ingsis.result.Result<String> acceptVisitor(com.ingsis.visitors.Visitor visitor) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }

        @Override
        public com.ingsis.result.Result<String> acceptInterpreter(
                com.ingsis.visitors.Interpreter interpreter) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }
    }

    @Test
    void returnsIdentifierFailureWhenIdentifierCheckerFails() {
        NodeEventHandler<IdentifierNode> idChecker =
                node -> new DefaultResultFactory().createIncorrectResult("id bad");
        NodeEventHandler<ExpressionNode> exprChecker =
                node -> new DefaultResultFactory().createCorrectResult("ok");

        DeclarationHandler handler = new DeclarationHandler(idChecker, exprChecker);

        IdentifierNode id = new IdentifierNode("a", 1, 2);
        ValueAssignationNode va = new ValueAssignationNode(id, new DummyExpression(), 1, 1);
        DeclarationKeywordNode decl = new DeclarationKeywordNode(null, va, false, 1, 1);

        Result<String> res = handler.handle(decl);
        assertFalse(res.isCorrect());
        assertEquals("id bad", res.error());
    }

    @Test
    void delegatesToExpressionWhenIdentifierOk() {
        NodeEventHandler<IdentifierNode> idChecker =
                node -> new DefaultResultFactory().createCorrectResult("ok");
        NodeEventHandler<ExpressionNode> exprChecker =
                node -> new DefaultResultFactory().createIncorrectResult("expr bad");

        DeclarationHandler handler = new DeclarationHandler(idChecker, exprChecker);

        IdentifierNode id = new IdentifierNode("a", 3, 4);
        ExpressionNode expr =
                new DummyExpression() {
                    @Override
                    public Integer line() {
                        return 5;
                    }

                    @Override
                    public Integer column() {
                        return 6;
                    }
                };
        ValueAssignationNode va = new ValueAssignationNode(id, expr, 1, 1);
        DeclarationKeywordNode decl = new DeclarationKeywordNode(null, va, false, 1, 1);

        Result<String> res = handler.handle(decl);
        assertFalse(res.isCorrect());
        assertEquals("expr bad", res.error());
    }
}
