package com.ingsis.engine.services;

import java.io.InputStream;

import com.ingsis.charstream.factories.CharStreamFactory;
import com.ingsis.engine.versions.Version;
import com.ingsis.interpreter.factory.InterpreterFactory;
import com.ingsis.interpreter.visitor.factory.DefaultInterpreterVisitorFactory;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.lexer.factories.LexerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_0;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_1;
import com.ingsis.parser.semantic.factories.SemanticFactory;
import com.ingsis.parser.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.parser.syntactic.factories.SyntacticFactory;
import com.ingsis.parser.syntactic.parsers.factory.InMemoryParserFactory;
import com.ingsis.utils.evalstate.env.factories.DefaultEnviromentFactory;
import com.ingsis.utils.evalstate.factories.DefaultEvalStateFactory;
import com.ingsis.utils.evalstate.factories.EvalStateFactory;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.LoggerIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.template.factories.DefaultTokenTemplateFactory;

public class ExecuteService {

  public Result<String> execute(Version version, OutputEmitter emitter, InputSupplier supplier, InputStream in) {
    SafeIterationResult<String> iterationResult = createProgramInterpreter(version, emitter, supplier)
        .fromInputStream(in).next();
    while (iterationResult.isCorrect()) {
      iterationResult = iterationResult.nextIterator().next();
    }
    if (!iterationResult.isCorrect() && !iterationResult.error().equals("EOL")) {
      return new IncorrectResult<>(iterationResult.error());
    }
    return new CorrectResult<String>("Program interpreted succesfully");
  }

  private SafeIteratorFactory<String> createProgramInterpreter(Version version, OutputEmitter emitter,
      InputSupplier supplier) {
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
    SafeIteratorFactory<Checkable> checkableIteratorFactory = new SyntacticFactory(
        tokenIteratorFactory,
        new DefaultParserChainFactory(
            new InMemoryParserFactory(
                new DefaultTokenTemplateFactory(),
                new DefaultNodeFactory())),
        iterationResultFactory,
        tokenFactory);
    SafeIteratorFactory<Interpretable> interpretableIteratorFactory = new SemanticFactory(
        checkableIteratorFactory,
        iterationResultFactory);
    InterpreterVisitorFactory interpreterFactory = new DefaultInterpreterVisitorFactory();
    EvalStateFactory evalStateFactory = new DefaultEvalStateFactory(new DefaultEnviromentFactory());
    return new InterpreterFactory(
        interpretableIteratorFactory,
        interpreterFactory,
        emitter,
        supplier,
        iterationResultFactory,
        evalStateFactory);
  }
}
