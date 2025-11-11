/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.engine.factories.charstream.DefaultCharStreamFactory;
import com.ingsis.engine.factories.interpreter.DefaultProgramInterpreterFactory;
import com.ingsis.engine.factories.interpreter.ProgramInterpreterFactory;
import com.ingsis.engine.factories.lexer.DefaultLexerFactory;
import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.engine.factories.semantic.DefaultSemanticFactory;
import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.engine.factories.syntactic.DefaultSyntacticFactory;
import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.engine.factories.tokenstream.DefaultTokenStreamFactory;
import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.engine.versions.Version;
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
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.syntactic.factories.ParserChainFactory;
import com.ingsis.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;
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
    @Option(names = "--file", description = "Path to a PrintScript file to execute.")
    private Path file;

    @Option(names = "--version", description = "PrintScript version to use.", required = true)
    private Version version;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliEngine()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (file != null) {
            runFile(file);
        } else {
            runREPL();
        }
    }

    private void runFile(Path file) {
        try {
            System.out.println("Interpreting file: " + file);
            buildFileInterpreter(file).interpret();
            IncorrectResult<?> executionError = DefaultRuntime.getInstance().getExecutionError();
            if (executionError != null) {
                System.out.println(executionError.error());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

                buildReplInterpreter(buffer).interpret();
                IncorrectResult<?> executionError =
                        DefaultRuntime.getInstance().getExecutionError();
                if (executionError != null) {
                    System.out.print(executionError.error());
                    System.out.print("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in REPL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ProgramInterpreterFactory createProgramInterpreterFactory() {
        CharStreamFactory charStreamFactory = new DefaultCharStreamFactory();
        TokenFactory tokenFactory = new DefaultTokensFactory();
        TokenizerFactory tokenizerFactory;
        switch (version) {
            case V1_0:
                tokenizerFactory = new FirstTokenizerFactory(tokenFactory);
                break;
            case V1_1:
                tokenizerFactory = new SecondTokenizerFactory(tokenFactory);
                break;
            default:
                throw new IllegalArgumentException("Unsupported version: " + version);
        }
        LexerFactory lexerFactory =
                new DefaultLexerFactory(
                        charStreamFactory, tokenizerFactory, DefaultRuntime.getInstance());
        TokenStreamFactory tokenStreamFactory = new DefaultTokenStreamFactory(lexerFactory);
        NodeFactory nodeFactory = new DefaultNodeFactory();
        ParserChainFactory parserChainFactory =
                new DefaultParserChainFactory(new DefaultParserFactory(tokenFactory, nodeFactory));
        SyntacticFactory syntacticFactory =
                new DefaultSyntacticFactory(tokenStreamFactory, parserChainFactory);
        SemanticFactory semanticFactory = new DefaultSemanticFactory(syntacticFactory);
        SolutionStrategyFactory solutionStrategyFactory =
                new DefaultSolutionStrategyFactory(DefaultRuntime.getInstance());
        InterpreterVisitorFactory interpreterVisitorFactory =
                new DefaultInterpreterVisitorFactory(solutionStrategyFactory);
        ProgramInterpreterFactory programInterpreterFactory =
                new DefaultProgramInterpreterFactory(semanticFactory, interpreterVisitorFactory);
        return programInterpreterFactory;
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
