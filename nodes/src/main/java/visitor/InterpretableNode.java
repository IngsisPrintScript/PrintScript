package visitor;

import common.Node;
import results.Result;

public interface InterpretableNode extends SemanticallyCheckable, Node {
    Result<String> acceptInterpreter(InterpretVisitor interpreter);
}
