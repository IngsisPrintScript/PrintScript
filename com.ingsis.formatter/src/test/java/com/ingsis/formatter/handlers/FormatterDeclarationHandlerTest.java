/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterDeclarationHandlerTest {
    private ResultFactory resultFactory;
    private NodeEventHandler<ExpressionNode> exprHandler;
    private FormatterDeclarationHandler handler;

    @BeforeEach
    void setup() {
        resultFactory = new DefaultResultFactory();
        exprHandler = node -> resultFactory.createCorrectResult("42");
        handler = new FormatterDeclarationHandler(true, true, true, exprHandler, resultFactory);
    }

    @Test
    void handleGeneratesDeclarationWithSpacesAndSemicolon() {
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        TypeNode type = new TypeNode(Types.NUMBER, 1, 1);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, type, 1, 1);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("42", 1, 2), 1, 1);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, true, 1, 1);

        CorrectResult<String> r = (CorrectResult<String>) handler.handle(decl);
        assertEquals("let x : NUMBER = 42 ;\n", r.result());
    }

    @Test
    void handleWhenExpressionFailsReturnsClonedIncorrect() {
        NodeEventHandler<ExpressionNode> bad = n -> resultFactory.createIncorrectResult("expr bad");
        FormatterDeclarationHandler badHandler =
                new FormatterDeclarationHandler(true, true, true, bad, resultFactory);
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        TypeNode type = new TypeNode(Types.NUMBER, 1, 1);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, type, 1, 1);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("42", 1, 2), 1, 1);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, true, 1, 1);

        IncorrectResult<String> r = (IncorrectResult<String>) badHandler.handle(decl);
        assertEquals("expr bad", r.error());
    }
}
