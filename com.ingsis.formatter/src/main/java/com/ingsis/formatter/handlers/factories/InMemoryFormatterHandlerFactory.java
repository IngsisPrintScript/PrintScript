/*
 * My Project
 */

package com.ingsis.formatter.handlers.factories;

import com.ingsis.formatter.handlers.FormatterConditionalHandler;
import com.ingsis.formatter.handlers.FormatterDeclarationHandler;
import com.ingsis.formatter.handlers.FormatterIdentifierHandler;
import com.ingsis.formatter.handlers.FormatterLiteralHandler;
import com.ingsis.formatter.handlers.FormatterOperatorHandler;
import com.ingsis.formatter.handlers.FormatterPrintlnHandler;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.AndInMemoryNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.OrInMemoryNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class InMemoryFormatterHandlerFactory implements HandlerFactory {
    private final Supplier<Checker> eventsCheckerSupplier;
    private final ResultFactory resultFactory;
    private final RuleStatusProvider ruleStatusProvider;

    public InMemoryFormatterHandlerFactory(
            ResultFactory resultFactory,
            RuleStatusProvider ruleStatusProvider,
            Supplier<Checker> eventsCheckerSupplier) {
        this.resultFactory = resultFactory;
        this.ruleStatusProvider = ruleStatusProvider;
        this.eventsCheckerSupplier = eventsCheckerSupplier;
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
        handlerRegistry.register(
                new FormatterConditionalHandler(eventsCheckerSupplier, resultFactory));
        return handlerRegistry;
    }

    @Override
    public NodeEventHandler<ExpressionNode> createExpressionHandler() {
        AtomicReference<NodeEventHandler<ExpressionNode>> ref = new AtomicReference<>();

        Supplier<NodeEventHandler<ExpressionNode>> self = ref::get;

        OrInMemoryNodeEventHandlerRegistry<ExpressionNode> registry =
                new OrInMemoryNodeEventHandlerRegistry<>(resultFactory);

        registry.register(new FormatterLiteralHandler(resultFactory));
        registry.register(new FormatterIdentifierHandler(resultFactory));

        registry.register(new FormatterOperatorHandler(resultFactory, self));
        registry.register(
                new FormatterPrintlnHandler(
                        ruleStatusProvider.getRuleValue("printlnSeparationLines", Integer.class),
                        self,
                        resultFactory));

        ref.set(registry);

        return registry;
    }
}
