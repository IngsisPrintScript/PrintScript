/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.engine.factories.charstream.DefaultCharStreamFactory;
import com.ingsis.engine.factories.formatter.InMemoryFormatterFactory;
import com.ingsis.engine.factories.interpreter.DefaultProgramInterpreterFactory;
import com.ingsis.engine.factories.interpreter.ProgramInterpreterFactory;
import com.ingsis.engine.factories.lexer.DefaultLexerFactory;
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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@SuppressFBWarnings(
        value = "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
        justification = "version is set by PicoCLI before use")
@CommandLine.Command(
        name = "cli-engine",
        mixinStandardHelpOptions = true,
        description = "Runs the interpreter with CLI input")
public final class CliEngine implements Engine {
    @Option(names = "--action", description = "Command to execute.", required = true)
    private String command;

    @Option(names = "--formatConfig", description = "Path to the format config file.")
    private Path formatConfig;

    @Option(names = "--file", description = "Path to a PrintScript file.")
    private Path file;

    @Option(names = "--version", description = "PrintScript version to use.", required = true)
    private Version version;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliEngine()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (command.equals("analyze")) {
            analyzeFile(file);
        } else if (command.equals("format")) {
            formatFile(file);
        } else {
            if (file != null) {
                runFile(file);
            } else {
                runREPL();
            }
        }
    }

    private void formatFile(Path file) {
        try {
            System.out.println("Format file: " + file);
            Result<String> formatResult = buildProgramFormatter(file).format();
            IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
            if (!formatResult.isCorrect() && executionError != null) {
                System.out.print("Error: " + executionError.error() + "\n");
            }
            System.out.print(formatResult.result());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private void analyzeFile(Path file) {
        try {
            System.out.println("Analyzing file: " + file);
            Result<String> analyzeResult = buildProgramSca(file).analyze();
            IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
            if (!analyzeResult.isCorrect() && executionError != null) {
                System.out.print("Error: " + executionError.error() + "\n");
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        System.out.print("Checks passed.");
    }

    private void runFile(Path file) {
        try {
            System.out.println("Interpreting file: " + file);
            Result<String> interpretResult = buildFileInterpreter(file).interpret();
            IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
            if (!interpretResult.isCorrect() && executionError != null) {
                System.out.print("Error: " + executionError.error() + "\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void runREPL() {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            DefaultRuntime.getInstance().push();
            System.out.println("Welcome to PrintScript REPL! Type 'exit' to quit.");

            String line;
            while (true) {
                System.out.print("> ");
                line = reader.readLine();
                if (line == null || line.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                Queue<Character> buffer = new ArrayDeque<>();
                line.chars().forEach(c -> buffer.add((char) c));

                Result<String> interpretResult = buildReplInterpreter(buffer).interpret();
                IncorrectResult<?> executionError =
                        DefaultRuntime.getInstance().getExecutionError();
                if (!interpretResult.isCorrect() && executionError != null) {
                    System.out.print("Error: " + executionError.error() + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in REPL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private SemanticFactory createSemanticFactory() {
        ResultFactory resultFactory =
                new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
        CharStreamFactory charStreamFactory = new DefaultCharStreamFactory();
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
        LexerFactory lexerFactory =
                new DefaultLexerFactory(
                        charStreamFactory, tokenizerFactory, DefaultRuntime.getInstance());
        TokenStreamFactory tokenStreamFactory =
                new DefaultTokenStreamFactory(lexerFactory, resultFactory);
        NodeFactory nodeFactory = new DefaultNodeFactory();
        ParserChainFactory parserChainFactory =
                new DefaultParserChainFactory(new DefaultParserFactory(tokenFactory, nodeFactory));
        SyntacticFactory syntacticFactory =
                new DefaultSyntacticFactory(tokenStreamFactory, parserChainFactory);
        return new DefaultSemanticFactory(syntacticFactory, resultFactory);
    }

    private ProgramInterpreterFactory createProgramInterpreterFactory() {
        ResultFactory resultFactory =
                new LoggerResultFactory(new DefaultResultFactory(), DefaultRuntime.getInstance());
        SemanticFactory semanticFactory = createSemanticFactory();
        SolutionStrategyFactory solutionStrategyFactory =
                new DefaultSolutionStrategyFactory(DefaultRuntime.getInstance());
        InterpreterVisitorFactory interpreterVisitorFactory =
                new DefaultInterpreterVisitorFactory(solutionStrategyFactory, resultFactory);
        ProgramInterpreterFactory programInterpreterFactory =
                new DefaultProgramInterpreterFactory(semanticFactory, interpreterVisitorFactory);
        return programInterpreterFactory;
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
                .createSca(
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
                        checkerSupplier // <-- IMPORTANT
                        );

        PublishersFactory publishersFactory = new InMemoryFormatterPublisherFactory(handlerFactory);

        Checker checker =
                new DefaultCheckerFactory().createInMemoryEventBasedChecker(publishersFactory);

        checkerRef.set(checker);

        return (EventsChecker) checker;
    }

    private ProgramFormatter buildProgramFormatter(Path file) throws IOException {
        return new InMemoryFormatterFactory()
                .createFormatter(
                        createSemanticFactory(),
                        file,
                        DefaultRuntime.getInstance(),
                        buildFormatterChecker());
    }

    private ProgramInterpreter buildReplInterpreter(Queue<Character> buffer) {
        return createProgramInterpreterFactory()
                .createCliProgramInterpreter(buffer, DefaultRuntime.getInstance());
    }

    private ProgramInterpreter buildFileInterpreter(Path file) throws IOException {
        return createProgramInterpreterFactory()
                .createFileProgramInterpreter(file, DefaultRuntime.getInstance());
    }
}
