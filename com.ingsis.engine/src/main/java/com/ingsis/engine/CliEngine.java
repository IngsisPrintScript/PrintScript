/*
 * My Project
 */

package com.ingsis.engine;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.engine.factories.charstream.DefaultCharStreamFactory;
import com.ingsis.engine.factories.interpreter.CodeInterpreterFactory;
import com.ingsis.engine.factories.interpreter.DefaultCodeInterpreterFactory;
import com.ingsis.engine.factories.lexer.DefaultLexerFactory;
import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.engine.factories.semantic.DefaultSemanticFactory;
import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.engine.factories.syntactic.DefaultSyntacticFactory;
import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.engine.factories.tokenstream.DefaultTokenStreamFactory;
import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.expression.strategies.factories.DefaultSolutionStrategyFactory;
import com.ingsis.interpreter.visitor.expression.strategies.factories.SolutionStrategyFactory;
import com.ingsis.interpreter.visitor.factory.DefaultInterpreterVisitorFactory;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.lexer.tokenizers.factories.DefaultTokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.nodes.factories.DefaultNodeFactory;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.result.Result;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.syntactic.factories.ParserChainFactory;
import com.ingsis.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import picocli.CommandLine;

@CommandLine.Command(
        name = "cli-engine",
        mixinStandardHelpOptions = true,
        description = "Runs the interpreter with CLI input")
public final class CliEngine implements Engine {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliEngine()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
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

                ProgramInterpreter interpreter = buildInterpreter(buffer);

                Result<String> result = interpreter.interpret();
                System.out.println(result);
            }
        } catch (Exception e) {
            System.err.println("Error in REPL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ProgramInterpreter buildInterpreter(Queue<Character> buffer) {
        CharStreamFactory charStreamFactory = new DefaultCharStreamFactory();
        TokenFactory tokenFactory = new DefaultTokensFactory();
        TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory(tokenFactory);
        LexerFactory lexerFactory = new DefaultLexerFactory(charStreamFactory, tokenizerFactory);
        TokenStreamFactory tokenStreamFactory = new DefaultTokenStreamFactory(lexerFactory);
        NodeFactory nodeFactory = new DefaultNodeFactory();
        ParserChainFactory parserChainFactory =
                new DefaultParserChainFactory(new DefaultParserFactory(tokenFactory, nodeFactory));
        SyntacticFactory syntacticFactory =
                new DefaultSyntacticFactory(tokenStreamFactory, parserChainFactory);
        SemanticFactory semanticFactory = new DefaultSemanticFactory(syntacticFactory);
        SolutionStrategyFactory solutionStrategyFactory = new DefaultSolutionStrategyFactory();
        InterpreterVisitorFactory interpreterVisitorFactory =
                new DefaultInterpreterVisitorFactory(solutionStrategyFactory);
        CodeInterpreterFactory codeInterpreterFactory =
                new DefaultCodeInterpreterFactory(semanticFactory, interpreterVisitorFactory);
        return codeInterpreterFactory.createCliProgramInterpreter(
                buffer, DefaultRuntime.getInstance());
    }
}
