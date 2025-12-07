/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.charstream.factories.CharStreamFactory;
import com.ingsis.engine.versions.Version;
import com.ingsis.formatter.factories.FormatterFactory;
import com.ingsis.formatter.factories.InMemoryFormatterFactory;
import com.ingsis.interpreter.visitor.factory.DefaultInterpreterVisitorFactory;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.lexer.factories.LexerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.parser.semantic.factories.SemanticFactory;
import com.ingsis.parser.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.parser.syntactic.factories.SyntacticFactory;
import com.ingsis.parser.syntactic.parsers.factory.ParserFactory;
import com.ingsis.parser.syntactic.factories.ParserChainFactory;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.runtime.result.factory.LoggerResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.sca.factories.DefaultScaFactory;
import com.ingsis.sca.factories.ScaFactory;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.status.provider.factories.InMemoryRuleStatusProviderFactory;
import java.io.InputStream;
import java.io.Writer;

public class InMemoryEngine implements Engine {

  @Override
  public Result<String> interpret(InputStream inputStream, Version version) {
    return createProgramInterpreterFactory(version).fromInputStream(inputStream).interpret();
  }

  @Override
  public Result<String> format(
      InputStream inputStream, InputStream config, Writer writer, Version version) {
    Result<String> formatResult = createFormatterFactory(version)
        .fromFile(
            inputStream,
            DefaultRuntime.getInstance(),
            new InMemoryRuleStatusProviderFactory()
                .createDefaultRuleStatusProvider(config),
            writer)
        .format();
    if (!formatResult.isCorrect()) {
      return formatResult;
    }
    return new CorrectResult<String>("Formatted succesfully.");
  }

  @Override
  public Result<String> analyze(InputStream inputStream, InputStream config, Version version) {
    return createScaFactory(version)
        .fromFile(
            inputStream,
            new InMemoryRuleStatusProviderFactory()
                .createDefaultRuleStatusProvider(config),
            DefaultRuntime.getInstance())
        .analyze();
  }

  private SafeIteratorFactory<Interpretable> createSemanticFactory(Version version) {
    TokenFactory tokenFactory = new DefaultTokensFactory();
    Runtime runtime = DefaultRuntime.getInstance();
    ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), runtime);
    SafeIteratorFactory<MetaChar> metaCharIteratorFactory = new CharStreamFactory();
    SafeIteratorFactory<Token> tokenIteratorFactory = createTokenIteratorFactory(
        metaCharIteratorFactory, resultFactory, tokenFactory, version);
    SafeIteratorFactory<Checkable> cheeckableIteratorFactory = createCheckableIteratorFactory(tokenIteratorFactory,
        tokenFactory);
    SafeIteratorFactory<Interpretable> interpretableFactory = new SemanticFactory(cheeckableIteratorFactory,
        resultFactory, runtime);
    return interpretableFactory;
  }

  private SafeIteratorFactory<Checkable> createCheckableIteratorFactory(
      SafeIteratorFactory<Token> tokenIteratorFactory, TokenFactory tokenFactory) {
    NodeFactory nodeFactory = new DefaultNodeFactory();
    ParserFactory parserFactory = new DefaultParserFactory(tokenFactory, nodeFactory);
    ParserChainFactory parserChainFactory = new DefaultParserChainFactory(parserFactory);
    return new SyntacticFactory(tokenIteratorFactory, parserChainFactory);
  }

  private SafeIteratorFactory<Token> createTokenIteratorFactory(
      SafeIteratorFactory<MetaChar> metaCharIteratorFactory,
      ResultFactory resultFactory,
      TokenFactory tokenFactory,
      Version version) {
    TokenizerFactory tokenizerFactory = createTokenizerFactoryFromVersion(version, tokenFactory, resultFactory);
    return new LexerFactory(metaCharIteratorFactory, tokenizerFactory, resultFactory);
  }

  private TokenizerFactory createTokenizerFactoryFromVersion(
      Version version, TokenFactory tokenFactory, ResultFactory resultFactory) {
    return switch (version) {
      case V1_0 -> new TokenizerFactoryV1_0(tokenFactory, resultFactory);
      case V1_1 -> new SecondTokenizerFactory(tokenFactory, resultFactory);
    };
  }

  private ProgramInterpreterFactory createProgramInterpreterFactory(Version version) {
    ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
    PeekableIteratorFactory<Interpretable> semanticFactory = createSemanticFactory(version);
    SolutionStrategyFactory solutionStrategyFactory = new DefaultSolutionStrategyFactory(DefaultRuntime.getInstance());
    InterpreterVisitorFactory interpreterVisitorFactory = new DefaultInterpreterVisitorFactory(solutionStrategyFactory,
        resultFactory);
    return new DefaultProgramInterpreterFactory(
        semanticFactory, interpreterVisitorFactory, DefaultRuntime.getInstance());
  }

  private ScaFactory createScaFactory(Version version) {
    return new DefaultScaFactory(createSemanticFactory(version));
  }

  private FormatterFactory createFormatterFactory(Version version) {
    return new InMemoryFormatterFactory(createSemanticFactory(version));
  }
}
