/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import org.junit.jupiter.api.Test;

class DefaultStaticCodeAnalyzerHandlerFactoryTest {

    @Test
    void createsHandlersAndTheyReturnCorrectOnDefaultNodes() {
        ResultFactory rf = new DefaultResultFactory();
        DefaultStaticCodeAnalyzerHandlerFactory f = new DefaultStaticCodeAnalyzerHandlerFactory(rf);

        NodeEventHandler<DeclarationKeywordNode> decl = f.createDeclarationHandler();
        NodeEventHandler<IfKeywordNode> cond = f.createConditionalHandler();
        NodeEventHandler<ExpressionNode> expr = f.createExpressionHandler();

        // FinalHandler returns correct
        com.ingsis.nodes.expression.literal.LiteralNode condLit =
                new com.ingsis.nodes.expression.literal.LiteralNode("true", 1, 1);
        assertTrue(
                cond.handle(
                                new IfKeywordNode(
                                        condLit, java.util.List.of(), java.util.List.of(), 1, 1))
                        .isCorrect());
        assertTrue(
                expr.handle(new com.ingsis.nodes.expression.literal.LiteralNode("5", 1, 1))
                        .isCorrect());

        // Declaration handler expects identifier pattern; provide a valid identifier to avoid
        // incorrect
        IdentifierNode id = new IdentifierNode("validName", 1, 1);
        com.ingsis.nodes.expression.literal.LiteralNode literal =
                new com.ingsis.nodes.expression.literal.LiteralNode("5", 1, 1);
        com.ingsis.nodes.expression.operator.ValueAssignationNode va =
                new com.ingsis.nodes.expression.operator.ValueAssignationNode(id, literal, 1, 1);
        DeclarationKeywordNode declNode = new DeclarationKeywordNode(null, va, false, 1, 1);
        assertTrue(decl.handle(declNode).isCorrect());
    }
}
