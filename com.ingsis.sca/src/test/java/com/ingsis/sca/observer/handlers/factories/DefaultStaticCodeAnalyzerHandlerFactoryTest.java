/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
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
        LiteralNode condLit = new LiteralNode("true", 1, 1);
        assertTrue(
                cond.handle(
                                new IfKeywordNode(
                                        condLit, java.util.List.of(), java.util.List.of(), 1, 1))
                        .isCorrect());
        assertTrue(expr.handle(new LiteralNode("5", 1, 1)).isCorrect());

        // Declaration handler expects identifier pattern; provide a valid identifier to avoid
        // incorrect
        IdentifierNode id = new IdentifierNode("validName", 1, 1);
        LiteralNode literal = new LiteralNode("5", 1, 1);
        ValueAssignationNode va = new ValueAssignationNode(id, literal, 1, 1);
        DeclarationKeywordNode declNode = new DeclarationKeywordNode(null, va, false, 1, 1);
        assertTrue(decl.handle(declNode).isCorrect());
    }
}
