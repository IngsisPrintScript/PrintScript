/*
 * My Project
 */

package com.ingsis.printscript.formatter;

import com.ingsis.printscript.astnodes.NilNode;
import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.declaration.TypeNode;
import com.ingsis.printscript.astnodes.expression.binary.AdditionNode;
import com.ingsis.printscript.astnodes.expression.binary.AssignationNode;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.function.argument.CallArgumentNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.astnodes.statements.PrintStatementNode;
import com.ingsis.printscript.astnodes.statements.function.DeclareFunctionNode;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.formatter.FormatterRules.SeparationFormatter;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;

public class FormatterVisitor implements VisitorInterface {

    private final SeparationFormatter formatterRules;

    public FormatterVisitor(SeparationFormatter rules) {
        formatterRules = rules;
    }

    @Override
    public Result<String> visit(LetStatementNode node) {
        StringBuilder sentence = new StringBuilder("let ");
        if (!node.hasExpression()) {
            Result<String> declaration = this.visit(node.ascription().result());
            return new CorrectResult<String>(
                    (sentence.append(declaration.result()).append(";").append("\n")).toString());
        }
        Result<String> declaration = this.visit(node.ascription().result());
        Result<String> expression = node.expression().result().accept(this);
        return new CorrectResult<>(
                (sentence.append(declaration.result())
                                .append(formatterRules.spaceBeforeAssignation())
                                .append("=")
                                .append(formatterRules.spaceAfterAssignation())
                                .append(expression.result())
                                .append(";")
                                .append("\n"))
                        .toString());
    }

    @Override
    public Result<String> visit(PrintStatementNode node) {
        StringBuilder sentence = new StringBuilder();
        sentence.append(formatterRules.jumpsBeforePrint());
        Result<String> expression = node.expression().result().accept(this);
        return new CorrectResult<String>(
                (sentence.append("println( ")
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
        return new CorrectResult<>(
                sentence.append(identifier.result())
                        .append(formatterRules.spaceBeforeColon())
                        .append(":")
                        .append(formatterRules.spaceAfterColon())
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
        return new CorrectResult<>(
                left.result()
                        + formatterRules.spaceBeforeAssignation()
                        + "="
                        + formatterRules.spaceAfterAssignation()
                        + right.result()
                        + ";"
                        + "\n");
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
    public Result<String> visit(CallArgumentNode node) {
        return new IncorrectResult<>("Not implemented yet.");
    }

    @Override
    public Result<String> visit(DeclareFunctionNode node) {
        return new IncorrectResult<>("Not implemented yet.");
    }

    @Override
    public Result<String> visit(CallFunctionNode node) {
        return new IncorrectResult<>("Not implemented yet.");
    }

    @Override
    public Result<String> visit(NilNode node) {
        return new IncorrectResult<>("Cuack");
    }
}
