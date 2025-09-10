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
import results.Result;
import tokenizers.factories.TokenizerFactory;
import visitor.InterpretableNode;
import visitor.SemanticallyCheckable;

import java.util.Iterator;
import java.util.concurrent.Callable;

@Command(
        name = "cliApp",
        mixinStandardHelpOptions = true,
        version = "cliApp 0",
        description = "Runs a CLI interpreter for printScript"
)
public class CliApp implements CliAppInterface, Callable<Integer> {

    public static void main(String[] args){
        int exitCode = new CommandLine(new CliApp()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Result<String> execute() {
        CliRepository characterIterator = new CliRepository();
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), characterIterator);
        Iterator<SemanticallyCheckable> checkableNodesIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        Iterator<InterpretableNode> interpretableNodesIterator = new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        InterpreterInterface interpreter = new Interpreter(new InterpretVisitor(), interpretableNodesIterator);
        return interpreter.interpreter();
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Welcome to the PrintScript CLI.\n"
                + "Write your code below. Each line will be added to the buffer.\n"
                + "Type 'exit' to close this CLI.");
        Result<String> result = new CliApp().execute();
        if (!result.isSuccessful()) {
            System.out.println("Failure: " + result.errorMessage());
            return 1;
        }
        return 0;
    }
}
