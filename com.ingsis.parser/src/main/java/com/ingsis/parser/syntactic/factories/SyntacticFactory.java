/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.SyntacticParser;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.token.Token;

import java.io.InputStream;

public final class SyntacticFactory implements SafeIteratorFactory<Checkable> {
  private final SafeIteratorFactory<Token> tokenIteratorFactory;
  private final ParserChainFactory parserChainFactory;
  private final IterationResultFactory iterationResultFactory;

  public SyntacticFactory(
      SafeIteratorFactory<Token> tokenIteratorFactory,
      ParserChainFactory parserFactory,
      IterationResultFactory iterationResultFactory) {
    this.tokenIteratorFactory = tokenIteratorFactory;
    this.parserChainFactory = parserFactory;
    this.iterationResultFactory = iterationResultFactory;
  }

  @Override
  public SafeIterator<Checkable> fromInputStream(InputStream in) {
    return new SyntacticParser(
        tokenIteratorFactory.fromInputStream(in),
        parserChainFactory.createDefaultChain(),
        iterationResultFactory);
  }
}
