package com.ingsis.sca.observer.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.sca.observer.handlers.factories.DefaultStaticCodeAnalyzerHandlerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultStaticCodeAnalyzerPublisherFactoryTest {

    @Test
    void publishersContainHandlersFromFactory() {
        ResultFactory rf = new DefaultResultFactory();
        DefaultStaticCodeAnalyzerHandlerFactory hf = new DefaultStaticCodeAnalyzerHandlerFactory(rf);
        DefaultStaticCodeAnalyzerPublisherFactory pf = new DefaultStaticCodeAnalyzerPublisherFactory(hf);

        var letPub = pf.createLetNodePublisher();
        var ifPub = pf.createConditionalNodePublisher();
        var exprPub = pf.createExpressionNodePublisher();

        // notify should return correct result for default valid nodes
        com.ingsis.nodes.expression.identifier.IdentifierNode id = new com.ingsis.nodes.expression.identifier.IdentifierNode("valid",1,1);
        com.ingsis.nodes.expression.literal.LiteralNode lit = new com.ingsis.nodes.expression.literal.LiteralNode("5",1,1);
        com.ingsis.nodes.expression.operator.ValueAssignationNode va = new com.ingsis.nodes.expression.operator.ValueAssignationNode(id, lit, 1,1);
        DeclarationKeywordNode declNode = new DeclarationKeywordNode(null, va, false, 1,1);

        assertTrue(letPub.notify(declNode).isCorrect());
        com.ingsis.nodes.keyword.IfKeywordNode ifNode = new com.ingsis.nodes.keyword.IfKeywordNode(lit, java.util.List.of(), java.util.List.of(), 1,1);
        assertTrue(ifPub.notify(ifNode).isCorrect());
        assertTrue(exprPub.notify(lit).isCorrect());
    }
}
