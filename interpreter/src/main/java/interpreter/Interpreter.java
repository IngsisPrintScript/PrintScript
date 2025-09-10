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
        while (interpretableNodeIterator.hasNext()) {
            InterpretableNode tree = interpretableNodeIterator.next();
            Result<String> interpretResult = tree.acceptInterpreter(interpretVisitor());
            if (!interpretResult.isSuccessful()){
                return new IncorrectResult<>(interpretResult.errorMessage());
            }
        }
        return new CorrectResult<>("Correctly interpreted the program.");
    }
}
