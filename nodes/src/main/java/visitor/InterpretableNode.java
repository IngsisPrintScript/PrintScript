package visitor;

import results.Result;

public interface InterpretableNode extends SemanticallyCheckable {
    Result<String> acceptInterpreter(InterpretVisitor interpreter);
}
