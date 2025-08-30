package parser;

import common.Node;
import responses.Result;

public interface SyntacticInterface {
    Result<? extends Node> buildAbstractSyntaxTree();
}
