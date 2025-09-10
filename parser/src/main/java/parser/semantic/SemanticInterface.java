package parser.semantic;

import common.PeekableIterator;
import visitor.InterpretableNode;
import visitor.SemanticallyCheckable;

public interface SemanticInterface extends PeekableIterator<InterpretableNode> {
    Boolean isSemanticallyValid(SemanticallyCheckable tree);
}
