package interpreter;

import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import visitor.InterpretVisitor;
import visitor.InterpretableNode;

import java.util.Iterator;

public record Interpreter(
        InterpretVisitor interpretVisitor,
        Iterator<InterpretableNode> interpretableNodeIterator
) implements InterpreterInterface {
    @Override
    public Result<String> interpreter() {
        Result<String> interpretResult = null;
        while (interpretableNodeIterator.hasNext()) {
            InterpretableNode tree = interpretableNodeIterator.next();
            interpretResult = tree.acceptInterpreter(interpretVisitor());
            if (!interpretResult.isSuccessful()){
                interpretResult = new IncorrectResult<>(interpretResult.errorMessage());
            }
        }
        if (interpretResult == null) {
            return new  IncorrectResult<>("That's not a valid PrintScript line.");
        }
        return interpretResult;
    }
}
