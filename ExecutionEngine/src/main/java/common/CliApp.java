package common;

import interpreter.Interpreter;
import interpreter.InterpreterInterface;
import interpreter.visitor.InterpretVisitor;
import lexer.Lexical;
import parser.Syntactic;
import parser.ast.builders.cor.ChanBuilder;
import picocli.CommandLine;
import repositories.CliRepository;
import results.Result;
import tokenizers.factories.TokenizerFactory;

import java.util.Iterator;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "cliApp", version = "cliApp 0",
description = "Runs a CLI interpreter for printScript")
public class CliApp implements CliAppInterface, Callable<Integer> {

    public static void main(String[] args){
        main(args);
    }

    @Override
    public Result<String> execute() {
        CliRepository characterIterator = new CliRepository();
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), characterIterator);
        Iterator<Node> nodeIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        InterpreterInterface interpreter = new Interpreter(new InterpretVisitor(), nodeIterator);
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
