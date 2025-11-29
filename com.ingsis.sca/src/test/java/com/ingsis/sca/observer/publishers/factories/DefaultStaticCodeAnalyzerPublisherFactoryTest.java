/*
 * My Project
 */

package com.ingsis.sca.observer.publishers.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.sca.observer.handlers.factories.DefaultStaticCodeAnalyzerHandlerFactory;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import org.junit.jupiter.api.Test;

class DefaultStaticCodeAnalyzerPublisherFactoryTest {

    @Test
    void publishersContainHandlersFromFactory() {
        ResultFactory rf = new DefaultResultFactory();
        DefaultStaticCodeAnalyzerHandlerFactory hf =
                new DefaultStaticCodeAnalyzerHandlerFactory(rf);
        DefaultStaticCodeAnalyzerPublisherFactory pf =
                new DefaultStaticCodeAnalyzerPublisherFactory(hf);

        var letPub = pf.createLetNodePublisher();
        var ifPub = pf.createConditionalNodePublisher();
        var exprPub = pf.createExpressionNodePublisher();

        // notify should return correct result for default valid nodes
        IdentifierNode id = new IdentifierNode("valid", 1, 1);
        LiteralNode lit = new LiteralNode("5", 1, 1);
        ValueAssignationNode va = new ValueAssignationNode(id, lit, 1, 1);
        DeclarationKeywordNode declNode = new DeclarationKeywordNode(null, va, false, 1, 1);

        assertTrue(letPub.notify(declNode).isCorrect());
        IfKeywordNode ifNode =
                new IfKeywordNode(lit, java.util.List.of(), java.util.List.of(), 1, 1);
        assertTrue(ifPub.notify(ifNode).isCorrect());
        assertTrue(exprPub.notify(lit).isCorrect());
    }
}
