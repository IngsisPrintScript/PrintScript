package com.ingsis.engine.services;

import java.io.InputStream;
import java.io.Writer;

import com.ingsis.charstream.factories.CharStreamFactory;
import com.ingsis.engine.versions.Version;
import com.ingsis.formatter.factories.DefaultFormatterFactory;
import com.ingsis.lexer.factories.LexerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_0;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_1;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.LoggerIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.factories.TokenFactory;

public class FormatService {
  public Result<String> format(Version version, InputStream in, Writer writer) {
    try {
      SafeIterationResult<String> iterationResult = createProgramFormatter(version).fromInputStream(in).next();
      while (iterationResult.isCorrect()) {
        writer.append(iterationResult.iterationResult());
        iterationResult = iterationResult.nextIterator().next();
      }
      if (!iterationResult.isCorrect() && !iterationResult.iterationResult().equals("EOL")) {
        return new IncorrectResult<>(iterationResult.iterationResult());
      }
      return new CorrectResult<String>("Input formatted.");
    } catch (Exception e) {
      return new IncorrectResult<>(e.getMessage());
    }
  }

  private SafeIteratorFactory<String> createProgramFormatter(Version version) {
    IterationResultFactory baseResultFactory = new DefaultIterationResultFactory();
    IterationResultFactory iterationResultFactory = new LoggerIterationResultFactory(baseResultFactory);
    SafeIteratorFactory<MetaChar> metacharIteratorFactory = new CharStreamFactory(iterationResultFactory);
    TokenFactory tokenFactory = new DefaultTokensFactory();
    TokenizerFactory tokenizerFactory = null;
    switch (version) {
      case V1_0 -> tokenizerFactory = new TokenizerFactoryV1_0(tokenFactory);
      case V1_1 -> tokenizerFactory = new TokenizerFactoryV1_1(tokenFactory);
    }
    SafeIteratorFactory<Token> tokenIteratorFactory = new LexerFactory(
        metacharIteratorFactory,
        tokenizerFactory,
        iterationResultFactory);
    return new DefaultFormatterFactory(tokenIteratorFactory, iterationResultFactory);
  }
}
