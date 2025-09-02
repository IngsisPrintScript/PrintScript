package parser.semantic;

import nodes.visitor.SemanticallyCheckable;

public interface SemanticInterface {
    Boolean isSemanticallyValid(SemanticallyCheckable tree);
}
