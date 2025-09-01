package visitor;

import common.NilNode;
import responses.Result;
import declaration.AscriptionNode;
import expression.identifier.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public interface VisitorInterface {
    Result<String> visit(LetStatementNode node);
    Result<String> visit(PrintStatementNode node);
    Result<String> visit(AscriptionNode node);
    Result<String> visit(AdditionNode node);
    Result<String> visit(LiteralNode node);
    Result<String> visit(IdentifierNode node);
    Result<String> visit(TypeNode node);
    Result<String> visit(NilNode node);

}
