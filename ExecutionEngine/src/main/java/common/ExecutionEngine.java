package common;

import interpreter.Interpreter;
import interpreter.InterpreterInterface;
import interpreter.visitor.InterpretVisitor;
import lexer.Lexical;
import parser.Syntactic;
import parser.ast.builders.cor.ChanBuilder;
import repositories.CliRepository;
import results.Result;
import tokenizers.factories.TokenizerFactory;

import java.util.Iterator;

public class ExecutionEngine implements ExecutionEngineInterface{

    public static void main(String[] args){
        new ExecutionEngine().execute();
    }

    @Override
    public Result<String> execute() {
        CliRepository characterIterator = new CliRepository();
        try {
            characterIterator.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), characterIterator);
        Iterator<Node> nodeIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        InterpreterInterface interpreter = new Interpreter(new InterpretVisitor(), nodeIterator);
        Result<String> result = interpreter.interpreter();
        System.out.println(result);
        return result;
    }
}
