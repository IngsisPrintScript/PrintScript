package formatter;

import common.NilNode;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import formatter.FormatterRules.FactoryFormatterRules;
import responses.CorrectResult;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.VisitorInterface;

public class FormatterVisitor implements VisitorInterface {

    private final FactoryFormatterRules formatterRules;

    public FormatterVisitor(FactoryFormatterRules rules) {
        formatterRules = rules;
    }

    @Override
    public Result<String> visit(LetStatementNode node) {
        StringBuilder sentence = new StringBuilder("let ");
        if(!node.hasExpression()){
            Result<String> declaration = this.visit(node.ascription().result());
            return new CorrectResult<String>((sentence.append((String) declaration.result())).toString());

        }
        Result<String> declaration = this.visit(node.ascription().result());
        Result<String> expression = node.expression().result().accept(this);
        return new CorrectResult<>((sentence
                .append(declaration.result())
                .append(expression.result())
                .append(";")
                .append("\n"))
                .toString());
    }

    @Override
    public Result<String> visit(PrintStatementNode node) {
        StringBuilder sentence = new StringBuilder();
        sentence.append(formatterRules.JumpsBeforePrint());
        Result<String> expression = node.expression().result().accept(this);
        return new CorrectResult<String>((sentence
                .append("println(")
                .append(expression.result())
                .append(")")
                .append(";")
                .append("\n")).toString());
    }

    @Override
    public Result<String> visit(AscriptionNode node) {
        StringBuilder sentence = new StringBuilder();
        Result<String> type = this.visit(node.type().result());
        Result<String> identifier = this.visit(node.identifier().result());
        return new CorrectResult<>(sentence
                .append(identifier.result())
                .append(formatterRules.SpaceBeforeColon())
                .append(type.result())
                .append(formatterRules.SpaceAfterColon())
                .toString());
    }

    @Override
    public Result<String> visit(AdditionNode node) {
        return null;
    }

    @Override
    public Result<String> visit(LiteralNode node) {
        return null;
    }

    @Override
    public Result<String> visit(IdentifierNode node) {
        return null;
    }

    @Override
    public Result<String> visit(TypeNode node) {
        return null;
    }

    @Override
    public Result<String> visit(NilNode node) {
        return null;
    }
}
