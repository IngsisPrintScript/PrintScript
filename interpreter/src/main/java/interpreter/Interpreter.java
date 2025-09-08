package interpreter;

import common.Node;
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
                return new IncorrectResult<String>(interpretResult.errorMessage());
            }
        }
        return new CorrectResult<String>("Correctly interpreted the program.");
    }
}
