/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.factories;

import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.ExpressionNodeEventVariableExistenceHandler;
import com.ingsis.parser.semantic.checkers.handlers.identifier.existance.LetNodeEventVariableExistenceHandler;
import com.ingsis.parser.semantic.checkers.handlers.identifier.type.LetNodeCorrectTypeEventHandler;
import com.ingsis.parser.semantic.checkers.handlers.operators.OperatorNodeValidityHandler;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.AndInMemoryNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.FinalHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.runtime.Runtime;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultHandlersFactory implements HandlerFactory {
    private final Runtime runtime;
    private final ResultFactory resultFactory;

    public DefaultHandlersFactory(Runtime runtime, ResultFactory resultFactory) {
        this.runtime = runtime;
        this.resultFactory = resultFactory;
    }

    @Override
    public NodeEventHandler<DeclarationKeywordNode> createDeclarationHandler() {
        NodeEventHandlerRegistry<DeclarationKeywordNode> handler =
                new AndInMemoryNodeEventHandlerRegistry<>(resultFactory);
        handler.register(new LetNodeEventVariableExistenceHandler(runtime, resultFactory));
        handler.register(new LetNodeCorrectTypeEventHandler(runtime, resultFactory));
        return handler;
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
                new AndInMemoryNodeEventHandlerRegistry<>(resultFactory);
        handlerRegistry.register(
                new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory));
        handlerRegistry.register(new OperatorNodeValidityHandler(runtime, resultFactory));
        return handlerRegistry;
    }
}
