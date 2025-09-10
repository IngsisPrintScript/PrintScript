package common;

import interpreter.Interpreter;
import interpreter.InterpreterInterface;
import interpreter.visitor.InterpretVisitor;
import lexer.Lexical;
import parser.Syntactic;
import parser.ast.builders.cor.ChanBuilder;
import parser.semantic.SemanticAnalyzer;
import parser.semantic.enforcers.SemanticRulesChecker;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import repositories.CliRepository;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import tokenizers.factories.TokenizerFactory;
import visitor.InterpretableNode;
import visitor.SemanticallyCheckable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Callable;

@Command(
        name = "cliApp",
        mixinStandardHelpOptions = true,
        version = "cliApp 0",
        description = "Runs a CLI interpreter for printScript"
)
public class CliApp implements CliAppInterface, Callable<Integer> {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args){
        int exitCode = new CommandLine(new CliApp()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Result<String> execute() {
        Queue<Character> buffer = new LinkedList<>();
        InterpreterInterface interpreter = getInterpreter(buffer);
        while (true) {
            try{
                String line = reader.readLine();
                if (line == null || "exit".equals(line.trim())) {
                    return new CorrectResult<>("Interpreter finished successfully.");
                }
                for (char c : line.toCharArray()) {
                    buffer.add(c);
                }
            } catch (IOException e){
                return new IncorrectResult<>(e.getMessage());
            }
            Result<String> interpretResult = interpreter.interpreter();
            if (!interpretResult.isSuccessful()){
                System.out.println(interpretResult.errorMessage());
            }
        }
    }

    private static InterpreterInterface getInterpreter(Queue<Character> buffer) {
        CliRepository characterIterator = new CliRepository(buffer);
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), characterIterator);
        Iterator<SemanticallyCheckable> checkableNodesIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        Iterator<InterpretableNode> interpretableNodesIterator = new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        return new Interpreter(new InterpretVisitor(), interpretableNodesIterator);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Welcome to the PrintScript CLI.\n"
                + "Write your code below. Each line will be added to the buffer.\n"
                + "Type 'exit' to close this CLI.");
        Result<String> result = new CliApp().execute();
        if (!result.isSuccessful()) {
            System.out.println("Error: " + result.errorMessage());
            return 1;
        }
        return 0;
    }
}
