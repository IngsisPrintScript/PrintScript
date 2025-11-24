/*
 * My Project
 */

package com.ingsis.formatter.handlers.factories;

import com.ingsis.formatter.handlers.FormatterDeclarationHandler;
import com.ingsis.formatter.handlers.FormatterIdentifierHandler;
import com.ingsis.formatter.handlers.FormatterLiteralHandler;
import com.ingsis.formatter.handlers.FormatterOperatorHandler;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.AndInMemoryNodeEventHandlerRegistry;
import com.ingsis.rule.observer.handlers.FinalHandler;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.rule.observer.handlers.OrInMemoryNodeEventHandlerRegistry;
import com.ingsis.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.rule.status.provider.RuleStatusProvider;

public class InMemoryFormatterHandlerFactory implements HandlerFactory {
    private final ResultFactory resultFactory;
    private final RuleStatusProvider ruleStatusProvider;

    public InMemoryFormatterHandlerFactory(
            ResultFactory resultFactory, RuleStatusProvider ruleStatusProvider) {
        this.resultFactory = resultFactory;
        this.ruleStatusProvider = ruleStatusProvider;
    }

    @Override
    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
        NodeEventHandlerRegistry<DeclarationKeywordNode> handlerRegistry =
                new AndInMemoryNodeEventHandlerRegistry<>(resultFactory);
        handlerRegistry.register(
                new FormatterDeclarationHandler(
                        ruleStatusProvider.getRuleStatus("hasPostAscriptionSpace"),
                        ruleStatusProvider.getRuleStatus("hasPostAscriptionSpace"),
                        ruleStatusProvider.getRuleStatus("isAssignationSpaced"),
                        this.createExpressionHandler(),
                        resultFactory));
        return handlerRegistry;
    }

    @Override
    public NodeEventHandler<IfKeywordNode> createConditionalHandler() {
        NodeEventHandlerRegistry<IfKeywordNode> handlerRegistry =
                new AndInMemoryNodeEventHandlerRegistry<>(resultFactory);
        handlerRegistry.register(new FinalHandler<>(resultFactory));
        return handlerRegistry;
    }

    @Override
    public NodeEventHandler<ExpressionNode> createExpressionHandler() {
        NodeEventHandlerRegistry<ExpressionNode> handlerRegistry =
                new OrInMemoryNodeEventHandlerRegistry<>(resultFactory);
        handlerRegistry.register(
                new FormatterOperatorHandler(resultFactory, createLeafExpressionHandler()));
        return handlerRegistry;
    }

    private NodeEventHandler<ExpressionNode> createLeafExpressionHandler() {
        NodeEventHandlerRegistry<ExpressionNode> handlerRegistry =
                new OrInMemoryNodeEventHandlerRegistry<>(resultFactory);
        handlerRegistry.register(new FormatterLiteralHandler(resultFactory));
        handlerRegistry.register(new FormatterIdentifierHandler(resultFactory));
        return handlerRegistry;
    }
}
