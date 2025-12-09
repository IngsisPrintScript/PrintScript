package com.ingsis.engine;

import com.ingsis.charstream.factories.CharStreamFactory;
import com.ingsis.engine.versions.Version;
import com.ingsis.formatter.factories.FormatterFactory;
import com.ingsis.formatter.factories.InMemoryFormatterFactory;
import com.ingsis.interpreter.factory.InterpreterFactory;
import com.ingsis.interpreter.visitor.factory.DefaultInterpreterVisitorFactory;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.lexer.factories.LexerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_0;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_1;
import com.ingsis.parser.semantic.factories.SemanticFactory;
import com.ingsis.parser.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.parser.syntactic.factories.ParserChainFactory;
import com.ingsis.parser.syntactic.factories.SyntacticFactory;
import com.ingsis.parser.syntactic.parsers.factory.InMemoryParserFactory;
import com.ingsis.parser.syntactic.parsers.factory.ParserFactory;
import com.ingsis.sca.factories.DefaultScaFactory;
import com.ingsis.sca.factories.ScaFactory;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.LoggerIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.status.provider.factories.InMemoryRuleStatusProviderFactory;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.runtime.result.factory.LoggerResultFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.template.factories.DefaultTokenTemplateFactory;
import java.io.InputStream;
import java.io.Writer;

public class InMemoryEngine implements Engine {
    private final ResultFactory resultFactory;
    private final IterationResultFactory iterationResultFactory;

    public InMemoryEngine() {
        this.resultFactory =
                new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
        this.iterationResultFactory =
                new LoggerIterationResultFactory(new DefaultIterationResultFactory());
    }

    public InMemoryEngine(
            ResultFactory resultFactory, IterationResultFactory iterationResultFactory) {
        this.resultFactory = resultFactory;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public Result<String> interpret(InputStream inputStream, Version version) {
        SafeIteratorFactory<String> programFactory = createProgramInterpreterFactory(version);
        SafeIterator<String> iterator = programFactory.fromInputStream(inputStream);

        SafeIterationResult<String> result = iterator.next();
        if (!result.isCorrect()) {
            if (!"EOL".equals(result.error())) {
                return new IncorrectResult<>(result.error());
            }
            return resultFactory.createCorrectResult("Interpreted succesfully.");
        }

        while (result.isCorrect()) {
            SafeIterator<String> nextIterator = result.nextIterator();
            result = nextIterator.next();

            if (!result.isCorrect() && !result.error().equals("EOL")) {
                return new IncorrectResult<>(result.error());
            }
        }
        return resultFactory.createCorrectResult("Interpreted succesfully.");
    }

    @Override
    public Result<String> format(
            InputStream inputStream, InputStream config, Writer writer, Version version) {
        Result<String> formatResult =
                createFormatterFactory(version)
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
        return new CorrectResult<>("Formatted succesfully.");
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


    public SafeIteratorFactory<Interpretable> createSemanticFactory(Version version) {
        TokenFactory tokenFactory = new DefaultTokensFactory();
        Runtime runtime = DefaultRuntime.getInstance();
        SafeIteratorFactory<MetaChar> metaCharIteratorFactory =
                new CharStreamFactory(iterationResultFactory);
        SafeIteratorFactory<Token> tokenIteratorFactory =
                createTokenIteratorFactory(
                        metaCharIteratorFactory, resultFactory, tokenFactory, version);
        SafeIteratorFactory<Checkable> checkableIteratorFactory =
                createCheckableIteratorFactory(tokenIteratorFactory, tokenFactory);
        return new SemanticFactory(
                checkableIteratorFactory, resultFactory, runtime, iterationResultFactory);
    }

    private SafeIteratorFactory<Checkable> createCheckableIteratorFactory(
            SafeIteratorFactory<Token> tokenIteratorFactory, TokenFactory tokenFactory) {
        NodeFactory nodeFactory = new DefaultNodeFactory();
        ParserFactory parserFactory =
                new InMemoryParserFactory(
                        new DefaultTokenTemplateFactory(),
                        nodeFactory,
                        tokenFactory,
                        iterationResultFactory,
                        resultFactory);
        ParserChainFactory parserChainFactory = new DefaultParserChainFactory(parserFactory);
        return new SyntacticFactory(
                tokenIteratorFactory,
                parserChainFactory,
                iterationResultFactory,
                tokenFactory,
                resultFactory);
    }

    private SafeIteratorFactory<Token> createTokenIteratorFactory(
            SafeIteratorFactory<MetaChar> metaCharIteratorFactory,
            ResultFactory resultFactory,
            TokenFactory tokenFactory,
            Version version) {
        TokenizerFactory tokenizerFactory =
                createTokenizerFactoryFromVersion(version, tokenFactory, resultFactory);
        return new LexerFactory(metaCharIteratorFactory, tokenizerFactory, iterationResultFactory);
    }

    private TokenizerFactory createTokenizerFactoryFromVersion(
            Version version, TokenFactory tokenFactory, ResultFactory resultFactory) {
        return switch (version) {
            case V1_0 -> new TokenizerFactoryV1_0(tokenFactory);
            case V1_1 -> new TokenizerFactoryV1_1(tokenFactory);
        };
    }

    private SafeIteratorFactory<String> createProgramInterpreterFactory(Version version) {
        SafeIteratorFactory<Interpretable> semanticFactory = createSemanticFactory(version);
        InterpreterVisitorFactory interpreterVisitorFactory =
                new DefaultInterpreterVisitorFactory(resultFactory);
        return new InterpreterFactory(
                semanticFactory,
                interpreterVisitorFactory,
                DefaultRuntime.getInstance(),
                iterationResultFactory);
    }

    private ScaFactory createScaFactory(Version version) {
        return new DefaultScaFactory(createSemanticFactory(version));
    }

    private FormatterFactory createFormatterFactory(Version version) {
        return new InMemoryFormatterFactory(createSemanticFactory(version));
    }
}