/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.SyntacticParser;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.template.factories.DefaultTokenTemplateFactory;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.type.TokenType;

import java.io.InputStream;
import java.util.List;

public final class SyntacticFactory implements SafeIteratorFactory<Checkable> {
  private final SafeIteratorFactory<Token> tokenIteratorFactory;
  private final ParserChainFactory parserChainFactory;
  private final IterationResultFactory iterationResultFactory;
  private final TokenFactory tokenFactory;
  private final ResultFactory resultFactory;

  public SyntacticFactory(
      SafeIteratorFactory<Token> tokenIteratorFactory,
      ParserChainFactory parserFactory,
      IterationResultFactory iterationResultFactory,
      TokenFactory tokenFactory,
      ResultFactory resultFactory) {
    this.tokenIteratorFactory = tokenIteratorFactory;
    this.parserChainFactory = parserFactory;
    this.iterationResultFactory = iterationResultFactory;
    this.tokenFactory = tokenFactory;
    this.resultFactory = resultFactory;
  }

  @Override
  public SafeIterator<Checkable> fromInputStream(InputStream in) {
    TokenTemplateFactory tokenTemplateFactory = new DefaultTokenTemplateFactory();
    return new SyntacticParser(
        tokenIteratorFactory.fromInputStream(in),
        parserChainFactory.createDefaultChain(),
        new DefaultTokenStream(
            List.of(tokenTemplateFactory.separator(TokenType.SPACE.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.NEWLINE.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.TAB.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.CRETURN.lexeme()).result()),
            tokenFactory,
            iterationResultFactory, resultFactory),
        iterationResultFactory);
  }
}
