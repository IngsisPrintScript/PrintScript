package common.visitor;

import common.nodes.statements.LetStatementNode;
import common.responses.Result;

public interface VisitorInterface {
    Result visit(LetStatementNode node);
}
