package parser.semantic;

import visitor.SemanticallyCheckable;

public interface SemanticInterface {
    Boolean isSemanticallyValid(SemanticallyCheckable tree);
}
