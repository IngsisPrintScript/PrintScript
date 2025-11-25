/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormatterDeclarationHandlerTest {
    private ResultFactory resultFactory;
    private NodeEventHandler<com.ingsis.nodes.expression.ExpressionNode> exprHandler;
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
        TypeNode type = new TypeNode(com.ingsis.types.Types.NUMBER, 1, 1);
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
        NodeEventHandler<com.ingsis.nodes.expression.ExpressionNode> bad =
                n -> resultFactory.createIncorrectResult("expr bad");
        FormatterDeclarationHandler badHandler =
                new FormatterDeclarationHandler(true, true, true, bad, resultFactory);
        IdentifierNode id = new IdentifierNode("x", 1, 1);
        TypeNode type = new TypeNode(com.ingsis.types.Types.NUMBER, 1, 1);
        TypeAssignationNode typeAssign = new TypeAssignationNode(id, type, 1, 1);
        ValueAssignationNode valueAssign =
                new ValueAssignationNode(id, new LiteralNode("42", 1, 2), 1, 1);
        DeclarationKeywordNode decl =
                new DeclarationKeywordNode(typeAssign, valueAssign, true, 1, 1);

        IncorrectResult<String> r = (IncorrectResult<String>) badHandler.handle(decl);
        assertEquals("expr bad", r.error());
    }
}
