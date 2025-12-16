/*
 * My Project
 */

package com.ingsis.formatter.factories;

import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.formatter.rules.*;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.token.Token;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DefaultFormatterFactory implements SafeIteratorFactory<String> {
  private final SafeIteratorFactory<Token> tokenIteratorFactory;
  private final IterationResultFactory iterationResultFactory;
  private final RuleStatusProvider ruleStatusProvider;

  public DefaultFormatterFactory(
      SafeIteratorFactory<Token> tokenIteratorFactory,
      IterationResultFactory iterationResultFactory,
      RuleStatusProvider ruleStatusProvider) {
    this.tokenIteratorFactory = tokenIteratorFactory;
    this.iterationResultFactory = iterationResultFactory;
    this.ruleStatusProvider = ruleStatusProvider;
  }

  @Override
  public SafeIterator<String> fromInputStream(InputStream in) {
    List<TriviaRule> triviaRules = new ArrayList<>();
    if (ruleStatusProvider.getRuleStatus("enforce-no-spacing-around-equals")) {
      triviaRules.add(new SpaceBeforeEquals(false));
      triviaRules.add(new SpaceAfterEquals(false));
    } else if (ruleStatusProvider.getRuleStatus("enforce-spacing-around-equals")) {
      triviaRules.add(new SpaceBeforeEquals(true));
      triviaRules.add(new SpaceAfterEquals(true));
    }
    if (ruleStatusProvider.getRuleStatus("enforce-spacing-after-colon-in-declaration")) {
      triviaRules.add(new SpaceAfterColon());
    }
    if (ruleStatusProvider.getRuleStatus("enforce-spacing-before-colon-in-declaration")) {
      triviaRules.add(new SpaceBeforeColon());
    }
    if (ruleStatusProvider.getRuleStatus("if-brace-same-line")){
      triviaRules.add(new BraceLine(true));
    } else if (ruleStatusProvider.getRuleStatus("if-brace-below-line")) {
      triviaRules.add(new BraceLine(false));
    }
    if (ruleStatusProvider.getRuleStatus("mandatory-line-break-after-statement")){
      triviaRules.add(new LineBreakAfterStatement());
    }
    if (ruleStatusProvider.getRuleStatus("mandatory-single-space-separation")) {
      triviaRules.add(new SingleSpaceInBetween());
    }
    if (ruleStatusProvider.getRuleStatus("mandatory-space-surrounding-operations")) {
      triviaRules.add(new SpaceBeforeOperator());
      triviaRules.add(new SpaceAfterOperator());
    }
    if (ruleStatusProvider.getRuleStatus("if-brace-below-line")) {
      triviaRules.add(new BraceLine(false));
    } else if (ruleStatusProvider.getRuleStatus("if-brace-same-line")) {
      triviaRules.add(new BraceLine(true));
    }
    triviaRules.add(new BaseRule());
    return new ProgramFormatter(
            tokenIteratorFactory.fromInputStream(in),
        iterationResultFactory, triviaRules,
            new LinesAfterFunctionCall(ruleStatusProvider.getRuleValue("line-breaks-after-println", Integer.class)));
  }

  @Override
  public SafeIterator<String> fromInputStreamLogger(InputStream in, String debugPath) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fromInputStreamLogger'");
  }
}
