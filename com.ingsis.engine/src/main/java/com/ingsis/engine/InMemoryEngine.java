/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.engine.factories.charstream.InMemoryCharStreamFactory;
import com.ingsis.engine.factories.formatter.FormatterFactory;
import com.ingsis.engine.factories.formatter.InMemoryFormatterFactory;
import com.ingsis.engine.factories.interpreter.DefaultProgramInterpreterFactory;
import com.ingsis.engine.factories.interpreter.ProgramInterpreterFactory;
import com.ingsis.engine.factories.lexer.InMemoryLexerFactory;
import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.engine.factories.sca.DefaultScaFactory;
import com.ingsis.engine.factories.sca.ScaFactory;
import com.ingsis.engine.factories.semantic.DefaultSemanticFactory;
import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.engine.factories.syntactic.DefaultSyntacticFactory;
import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.engine.factories.tokenstream.DefaultTokenStreamFactory;
import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.interpreter.visitor.expression.strategies.factories.DefaultSolutionStrategyFactory;
import com.ingsis.interpreter.visitor.expression.strategies.factories.SolutionStrategyFactory;
import com.ingsis.interpreter.visitor.factory.DefaultInterpreterVisitorFactory;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.lexer.tokenizers.factories.SecondTokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.parser.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.parser.syntactic.factories.ParserChainFactory;
import com.ingsis.parser.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.result.factory.LoggerResultFactory;
import com.ingsis.utils.nodes.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.rule.status.provider.YamlRuleStatusProvider;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import java.io.InputStream;
import java.io.Writer;

public class InMemoryEngine implements Engine {

  @Override
  public Result<String> interpret(InputStream inputStream) {
    return createProgramInterpreterFactory().fromInputStream(inputStream).interpret();
  }

  @Override
  public Result<String> format(InputStream inputStream, InputStream config, Writer writer) {
    Result<String> formatResult = createFormatterFactory()
        .fromFile(inputStream, DefaultRuntime.getInstance(), new YamlRuleStatusProvider(config)).format();
    if (!formatResult.isCorrect()) {
      return formatResult;
    }
    try {
      writer.write(formatResult.result());
    } catch (Exception e) {
      return new IncorrectResult<>(e.getMessage());
    }
    return new CorrectResult<String>("Formatted succesfully.");
  }

  @Override
  public Result<String> analyze(InputStream inputStream, InputStream config) {
    RuleStatusProvider ruleStatusProvider = new YamlRuleStatusProvider(config);
    return createScaFactory().fromFile(inputStream, ruleStatusProvider, DefaultRuntime.getInstance()).analyze();
  }

  private SemanticFactory createSemanticFactory() {
    ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
    CharStreamFactory charStreamFactory = new InMemoryCharStreamFactory();
    TokenFactory tokenFactory = new DefaultTokensFactory();
    TokenizerFactory tokenizerFactory = new SecondTokenizerFactory(tokenFactory, resultFactory);
    LexerFactory lexerFactory = new InMemoryLexerFactory(charStreamFactory, tokenizerFactory);
    TokenStreamFactory tokenStreamFactory = new DefaultTokenStreamFactory(lexerFactory, resultFactory);
    NodeFactory nodeFactory = new DefaultNodeFactory();
    ParserChainFactory parserChainFactory = new DefaultParserChainFactory(
        new DefaultParserFactory(tokenFactory, nodeFactory));
    SyntacticFactory syntacticFactory = new DefaultSyntacticFactory(tokenStreamFactory, parserChainFactory);
    return new DefaultSemanticFactory(
        syntacticFactory, resultFactory, DefaultRuntime.getInstance());
  }

  private ProgramInterpreterFactory createProgramInterpreterFactory() {
    ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
    SemanticFactory semanticFactory = createSemanticFactory();
    SolutionStrategyFactory solutionStrategyFactory = new DefaultSolutionStrategyFactory(DefaultRuntime.getInstance());
    InterpreterVisitorFactory interpreterVisitorFactory = new DefaultInterpreterVisitorFactory(solutionStrategyFactory,
        resultFactory);
    return new DefaultProgramInterpreterFactory(
        semanticFactory, interpreterVisitorFactory, DefaultRuntime.getInstance());
  }

  private ScaFactory createScaFactory() {
    return new DefaultScaFactory(createSemanticFactory());
  }

  private FormatterFactory createFormatterFactory() {
    return new InMemoryFormatterFactory(createSemanticFactory());
  }
}
