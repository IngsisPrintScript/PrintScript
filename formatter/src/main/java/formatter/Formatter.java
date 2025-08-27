package formatter;

import common.nodes.NilNode;
import common.nodes.declaration.AscriptionNode;
import common.nodes.declaration.IdentifierNode;
import common.nodes.declaration.TypeNode;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.LetStatementNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class Formatter implements VisitorInterface {
    @Override
    public Result visit(LetStatementNode node) {
        return null;
    }

    @Override
    public Result visit(PrintStatementNode node) {
        return null;
    }

    @Override
    public Result visit(AscriptionNode node) {
        return null;
    }

    @Override
    public Result visit(AdditionNode node) {
        return null;
    }

    @Override
    public Result visit(LiteralNode node) {
        return null;
    }

    @Override
    public Result visit(IdentifierNode node) {
        return null;
    }

    @Override
    public Result visit(TypeNode node) {
        return null;
    }

    @Override
    public Result visit(NilNode node) {
        return null;
    }


}
