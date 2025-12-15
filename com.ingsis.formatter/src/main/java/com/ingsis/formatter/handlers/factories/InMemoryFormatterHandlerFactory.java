/*
 * My Project
 */

package com.ingsis.formatter.handlers.factories;

import com.ingsis.formatter.handlers.FormatterConditionalHandler;
import com.ingsis.formatter.handlers.FormatterDeclarationHandler;
import com.ingsis.formatter.handlers.FormatterFunctionCallHandler;
import com.ingsis.formatter.handlers.FormatterIdentifierHandler;
import com.ingsis.formatter.handlers.FormatterLiteralHandler;
import com.ingsis.formatter.handlers.FormatterOperatorHandler;
import com.ingsis.formatter.handlers.FormatterSpecialFunctionCallHandler;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.AndNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.OrNodeEventHandlerRegistry;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerSupplier;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.token.template.factories.DefaultTokenTemplateFactory;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.type.TokenType;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class InMemoryFormatterHandlerFactory implements HandlerSupplier {
  private final Supplier<Checker> eventsCheckerSupplier;
  private final ResultFactory resultFactory;
  private final RuleStatusProvider ruleStatusProvider;
  private final Writer writer;

  public InMemoryFormatterHandlerFactory(
      ResultFactory resultFactory,
      RuleStatusProvider ruleStatusProvider,
      Supplier<Checker> eventsCheckerSupplier,
      Writer writer) {
    this.resultFactory = resultFactory;
    this.ruleStatusProvider = ruleStatusProvider;
    this.eventsCheckerSupplier = eventsCheckerSupplier;
    this.writer = writer;
  }

  @Override
  public NodeEventHandler<DeclarationKeywordNode> getDeclarationHandler() {
    NodeEventHandlerRegistry<DeclarationKeywordNode> handlerRegistry = new AndNodeEventHandlerRegistry<>(
        resultFactory);
    handlerRegistry.register(
        new FormatterDeclarationHandler(
            ruleStatusProvider.getRuleStatus(
                "enforce-spacing-before-colon-in-declaration"),
            ruleStatusProvider.getRuleStatus(
                "enforce-spacing-after-colon-in-declaration"),
            ruleStatusProvider.getRuleStatus("enforce-no-spacing-around-equals"),
            ruleStatusProvider.getRuleStatus("enforce-spacing-around-equals"),
            ruleStatusProvider.getRuleStatus("mandatory-single-space-separation"),
            this.getExpressionHandler(),
            resultFactory,
            writer,
            new DefaultTokenTemplateFactory()
                .separator(TokenType.SPACE.lexeme())
                .result()));
    return handlerRegistry;
  }

  @Override
  public NodeEventHandler<IfKeywordNode> getConditionalHandler() {
    TokenTemplateFactory tokenTemplateFactory = new DefaultTokenTemplateFactory();
    NodeEventHandlerRegistry<IfKeywordNode> handlerRegistry = new AndNodeEventHandlerRegistry<>(resultFactory);
    handlerRegistry.register(
        new FormatterConditionalHandler(
            eventsCheckerSupplier,
            ruleStatusProvider.getRuleStatus("mandatory-single-space-separation"),
            ruleStatusProvider.getRuleStatus("if-brace-same-line"),
            ruleStatusProvider.getRuleStatus("if-brace-below-line"),
            ruleStatusProvider.getRuleValue("indent-inside-if", Integer.class),
            tokenTemplateFactory.separator(TokenType.SPACE.lexeme()).result(),
            tokenTemplateFactory.separator(TokenType.NEWLINE.lexeme()).result(),
            tokenTemplateFactory.separator(TokenType.TAB.lexeme()).result(),
            resultFactory,
            writer));
    return handlerRegistry;
  }

  @Override
  public NodeEventHandler<ExpressionNode> getExpressionHandler() {
    TokenTemplateFactory tokenTemplateFactory = new DefaultTokenTemplateFactory();
    AtomicReference<NodeEventHandler<ExpressionNode>> ref = new AtomicReference<>();

    Supplier<NodeEventHandler<ExpressionNode>> self = ref::get;

    OrNodeEventHandlerRegistry<ExpressionNode> registry = new OrNodeEventHandlerRegistry<>(
        resultFactory);

    registry.register(new FormatterLiteralHandler(resultFactory, writer));
    registry.register(new FormatterIdentifierHandler(resultFactory, writer));

    FormatterFunctionCallHandler baseFunctionHandler = new FormatterFunctionCallHandler(
        self,
        ruleStatusProvider.getRuleStatus("mandatory-single-space-separation"),
        tokenTemplateFactory.separator(TokenType.SPACE.lexeme()).result(),
        tokenTemplateFactory.separator(TokenType.NEWLINE.lexeme()).result(),
        resultFactory,
        writer);

    registry.register(
        new FormatterSpecialFunctionCallHandler(
            ruleStatusProvider.getRuleValue("line-breaks-after-println", Integer.class),
            "println",
            ruleStatusProvider.getRuleStatus("mandatory-single-space-separation"),
            self,
            baseFunctionHandler,
            resultFactory,
            writer));
    registry.register(baseFunctionHandler);
    registry.register(new FormatterOperatorHandler(resultFactory, self, writer));
    ref.set(registry);

    return registry;
  }
}
