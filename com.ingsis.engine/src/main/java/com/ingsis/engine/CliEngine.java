/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.engine.factories.charstream.InMemoryCharStreamFactory;
import com.ingsis.engine.factories.formatter.InMemoryFormatterFactory;
import com.ingsis.engine.factories.interpreter.DefaultProgramInterpreterFactory;
import com.ingsis.engine.factories.interpreter.ProgramInterpreterFactory;
import com.ingsis.engine.factories.lexer.InMemoryLexerFactory;
import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.engine.factories.sca.DefaultScaFactory;
import com.ingsis.engine.factories.semantic.DefaultSemanticFactory;
import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.engine.factories.syntactic.DefaultSyntacticFactory;
import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.engine.factories.tokenstream.DefaultTokenStreamFactory;
import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.engine.versions.Version;
import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.formatter.handlers.factories.InMemoryFormatterHandlerFactory;
import com.ingsis.formatter.publishers.factories.InMemoryFormatterPublisherFactory;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.expression.strategies.factories.DefaultSolutionStrategyFactory;
import com.ingsis.interpreter.visitor.expression.strategies.factories.SolutionStrategyFactory;
import com.ingsis.interpreter.visitor.factory.DefaultInterpreterVisitorFactory;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.lexer.tokenizers.factories.FirstTokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.SecondTokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.nodes.factories.DefaultNodeFactory;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.LoggerResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.rule.observer.factories.DefaultCheckerFactory;
import com.ingsis.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.rule.status.provider.YamlRuleStatusProvider;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.sca.ProgramSca;
import com.ingsis.sca.observer.handlers.factories.DefaultStaticCodeAnalyzerHandlerFactory;
import com.ingsis.sca.observer.publishers.factories.DefaultStaticCodeAnalyzerPublisherFactory;
import com.ingsis.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.syntactic.factories.ParserChainFactory;
import com.ingsis.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.visitors.Checker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@SuppressWarnings("FieldMayBeFinal")
@CommandLine.Command(
        name = "cli-engine",
        mixinStandardHelpOptions = true,
        description = "Runs the interpreter with CLI input")
public final class CliEngine implements Engine {

    @Option(names = "--repl-mode", description = "Mode.", required = true)
    public Boolean replMode = true;

    @Option(names = "--action", description = "Command to execute.", required = true)
    public String command;

    @Option(names = "--file", description = "Path to a PrintScript file.")
    public Path file;

    @Option(names = "--formatConfig", description = "Path to the format config file.")
    public Path formatConfig;

    @Option(names = "--version", description = "PrintScript version to use.", required = true)
    public Version version;

    private volatile boolean stopRequested = false;

    public void requestStop() {
        stopRequested = true;
    }

    public CliEngine() {}

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliEngine()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            if ("analyze".equals(command)) {
                analyzeFile(file);
            } else if ("format".equals(command)) {
                formatFile(file);
            } else {
                if (file != null) {
                    runFile(file);
                } else {
                    runREPL();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void formatFile(Path file) throws IOException {
        System.out.println("Format file: " + file);
        ProgramFormatter formatter = buildProgramFormatter(file);
        Result<String> formatResult = formatter.format();
        IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
        if (!formatResult.isCorrect() && executionError != null) {
            System.out.print("Error: " + executionError.error() + "\n");
        }
        System.out.print(formatResult.result());
    }

    private void analyzeFile(Path file) throws IOException {
        System.out.println("Analyzing file: " + file);
        Result<String> analyzeResult = buildProgramSca(file).analyze();
        IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
        if (!analyzeResult.isCorrect() && executionError != null) {
            System.out.print("Error: " + executionError.error() + "\n");
        } else {
            System.out.print("Checks passed.");
        }
    }

    private void runFile(Path file) throws IOException {
        System.out.println("Interpreting file: " + file);
        Result<String> interpretResult = buildFileInterpreter(file).interpret();
        IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
        if (!interpretResult.isCorrect() && executionError != null) {
            System.out.print("Error: " + executionError.error() + "\n");
        }
    }

    private void runREPL() {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            DefaultRuntime.getInstance().push();
            if (replMode) System.out.println("Welcome to PrintScript REPL! Type 'exit' to quit.");

            String line;
            while (!stopRequested) {
                if (replMode) System.out.print("> ");
                line = reader.readLine();
                if (line == null || (replMode && line.equalsIgnoreCase("exit"))) {
                    if (replMode) System.out.println("Goodbye!");
                    break;
                }

                // Pass the line string directly to the interpreter
                Result<String> interpretResult = buildReplInterpreter(line).interpret();
                IncorrectResult<?> executionError =
                        DefaultRuntime.getInstance().getExecutionError();
                if (!interpretResult.isCorrect() && executionError != null) {
                    System.err.print("Error: " + executionError.error() + "\n");
                }
            }

            if (replMode) System.out.println("REPL stopped.");
        } catch (IOException e) {
            System.err.println("Error in REPL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private SemanticFactory createSemanticFactory() {
        ResultFactory resultFactory =
                new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
        CharStreamFactory charStreamFactory = new InMemoryCharStreamFactory();
        TokenFactory tokenFactory = new DefaultTokensFactory();
        TokenizerFactory tokenizerFactory;
        switch (version) {
            case V1_0:
                tokenizerFactory = new FirstTokenizerFactory(tokenFactory, resultFactory);
                break;
            case V1_1:
                tokenizerFactory = new SecondTokenizerFactory(tokenFactory, resultFactory);
                break;
            default:
                throw new IllegalArgumentException("Unsupported version: " + version);
        }
        LexerFactory lexerFactory = new InMemoryLexerFactory(charStreamFactory, tokenizerFactory);
        TokenStreamFactory tokenStreamFactory =
                new DefaultTokenStreamFactory(lexerFactory, resultFactory);
        NodeFactory nodeFactory = new DefaultNodeFactory();
        ParserChainFactory parserChainFactory =
                new DefaultParserChainFactory(new DefaultParserFactory(tokenFactory, nodeFactory));
        SyntacticFactory syntacticFactory =
                new DefaultSyntacticFactory(tokenStreamFactory, parserChainFactory);
        return new DefaultSemanticFactory(
                syntacticFactory, resultFactory, DefaultRuntime.getInstance());
    }

    private ProgramInterpreterFactory createProgramInterpreterFactory() {
        ResultFactory resultFactory =
                new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
        SemanticFactory semanticFactory = createSemanticFactory();
        SolutionStrategyFactory solutionStrategyFactory =
                new DefaultSolutionStrategyFactory(DefaultRuntime.getInstance());
        InterpreterVisitorFactory interpreterVisitorFactory =
                new DefaultInterpreterVisitorFactory(solutionStrategyFactory, resultFactory);
        return new DefaultProgramInterpreterFactory(
                semanticFactory, interpreterVisitorFactory, DefaultRuntime.getInstance());
    }

    private EventsChecker buildScaChecker() {
        return (EventsChecker)
                new DefaultCheckerFactory()
                        .createInMemoryEventBasedChecker(
                                new DefaultStaticCodeAnalyzerPublisherFactory(
                                        new DefaultStaticCodeAnalyzerHandlerFactory(
                                                new LoggerResultFactory(
                                                        new DefaultResultFactory(),
                                                        DefaultRuntime.getInstance()))));
    }

    private ProgramSca buildProgramSca(Path file) throws IOException {
        return new DefaultScaFactory()
                .fromFile(
                        createSemanticFactory(),
                        file,
                        DefaultRuntime.getInstance(),
                        buildScaChecker());
    }

    private EventsChecker buildFormatterChecker() {
        AtomicReference<Checker> checkerRef = new AtomicReference<>();
        Supplier<Checker> checkerSupplier = checkerRef::get;

        HandlerFactory handlerFactory =
                new InMemoryFormatterHandlerFactory(
                        new LoggerResultFactory(
                                new DefaultResultFactory(), DefaultRuntime.getInstance()),
                        new YamlRuleStatusProvider(formatConfig),
                        checkerSupplier);

        PublishersFactory publishersFactory = new InMemoryFormatterPublisherFactory(handlerFactory);

        Checker checker =
                new DefaultCheckerFactory().createInMemoryEventBasedChecker(publishersFactory);
        checkerRef.set(checker);

        return (EventsChecker) checker;
    }

    private ProgramFormatter buildProgramFormatter(Path file) throws IOException {
        return new InMemoryFormatterFactory()
                .fromFile(
                        createSemanticFactory(),
                        file,
                        DefaultRuntime.getInstance(),
                        buildFormatterChecker());
    }

    private ProgramInterpreter buildReplInterpreter(String line) throws IOException {
        return createProgramInterpreterFactory().fromString(line);
    }

    private ProgramInterpreter buildFileInterpreter(Path file) throws IOException {
        return createProgramInterpreterFactory().fromFile(file);
    }
}
