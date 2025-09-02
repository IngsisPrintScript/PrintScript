package visitor;

import results.Result;

public interface InterpretableNode {
    Result<String> acceptInterpreter(InterpretVisitor interpreter);
}
