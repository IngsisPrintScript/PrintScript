package formatter;

import common.NilNode;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.binary.AdditionNode;
import expression.binary.AssignationNode;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import formatter.FormatterRules.SeparationFormatter;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.VisitorInterface;

public class FormatterVisitor implements VisitorInterface {

    private final SeparationFormatter formatterRules;

    public FormatterVisitor(SeparationFormatter rules) {
        formatterRules = rules;
    }

    @Override
    public Result<String> visit(LetStatementNode node) {
        StringBuilder sentence = new StringBuilder("let ");
        if(!node.hasExpression()){
            Result<String> declaration = this.visit(node.ascription().result());
            return new CorrectResult<String>((sentence
                    .append(declaration.result())
                    .append(";")
                    .append("\n"))
                    .toString());

        }
        Result<String> declaration = this.visit(node.ascription().result());
        Result<String> expression = node.expression().result().accept(this);
        return new CorrectResult<>((sentence
                .append(declaration.result())
                .append(formatterRules.SpaceBeforeAssignation())
                .append("=")
                .append(formatterRules.SpaceAfterAssignation())
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
                .append("println( ")
                .append(expression.result())
                .append(" )")
                .append(";")
                .append("\n"))
                .toString());
    }

    @Override
    public Result<String> visit(AscriptionNode node) {
        StringBuilder sentence = new StringBuilder();
        Result<String> type = this.visit(node.type().result());
        Result<String> identifier = this.visit(node.identifier().result());
        return new CorrectResult<>(sentence
                .append(identifier.result())
                .append(formatterRules.SpaceBeforeColon())
                .append(":")
                .append(formatterRules.SpaceAfterColon())
                .append(type.result())
                .toString());
    }

    @Override
    public Result<String> visit(AdditionNode node) {
        Result<String> left = node.getLeftChild().result().accept(this);
        Result<String> right = node.getRightChild().result().accept(this);
        return new CorrectResult<>(left.result() + " + " + right.result());
    }

    @Override
    public Result<String> visit(AssignationNode node) {
        Result<String> right = node.getRightChild().result().accept(this);
        Result<String> left = node.getLeftChild().result().accept(this);
        return new CorrectResult<>(left.result() + formatterRules.SpaceBeforeAssignation() + "=" + formatterRules.SpaceAfterAssignation() + right.result() + ";" + "\n");
    }

    @Override
    public Result<String> visit(LiteralNode node) {
        return new CorrectResult<>(node.value());
    }

    @Override
    public Result<String> visit(IdentifierNode node) {
        return new CorrectResult<>(node.name());
    }

    @Override
    public Result<String> visit(TypeNode node) {
        return new CorrectResult<>(node.type());
    }

    @Override
    public Result<String> visit(NilNode node) {
        return new IncorrectResult<>("Cuack");
    }
}
