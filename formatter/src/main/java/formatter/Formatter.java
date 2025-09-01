package formatter;

import common.NilNode;
import declaration.AscriptionNode;
import declaration.IdentifierNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import responses.CorrectResult;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.VisitorInterface;

public class Formatter implements VisitorInterface {
    @Override
    public Result visit(LetStatementNode node) {
        StringBuilder input = new StringBuilder("let ");

        if(!node.hasExpression()){
            return input + this.visit((node.ascription()));
        }
        return new CorrectResult<>(input);
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
