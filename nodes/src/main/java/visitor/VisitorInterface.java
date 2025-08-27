package common;

import common.responses.Result;
import declaration.AscriptionNode;
import declaration.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public interface VisitorInterface {
    Result visit(LetStatementNode node);
    Result visit(PrintStatementNode node);
    Result visit(AscriptionNode node);
    Result visit(AdditionNode node);
    Result visit(LiteralNode node);
    Result visit(IdentifierNode node);
    Result visit(TypeNode node);
    Result visit(NilNode node);

}
