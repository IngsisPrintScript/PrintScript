/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import org.junit.jupiter.api.Test;

class DeclarationHandlerTest {

    static class DummyExpression implements ExpressionNode {
        @Override
        public java.util.List<ExpressionNode> children() {
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
        public Result<String> acceptChecker(Checker checker) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }

        @Override
        public Result<String> acceptVisitor(Visitor visitor) {
            return new DefaultResultFactory().createCorrectResult("ok");
        }

        @Override
        public Result<String> acceptInterpreter(Interpreter interpreter) {
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
