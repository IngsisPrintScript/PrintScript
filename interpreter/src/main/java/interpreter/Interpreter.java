package interpreter;

import common.Node;
import results.IncorrectResult;
import results.Result;
import visitor.InterpretVisitor;
import visitor.InterpretableNode;

public record Interpreter(InterpretVisitor interpretVisitor) implements InterpreterInterface {

    @Override
    public Result<String> interpreter(Node tree) {
        if (tree instanceof InterpretableNode interpretableTree) {
            return interpretableTree.acceptInterpreter(interpretVisitor());
        }
        return new IncorrectResult<String>("Passed node was not interpretable.");
    }
}
