/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.operator.TypeAssignationNode;
import com.ingsis.nodes.operator.ValueAssignationNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

public record LetKeywordNode(
        TypeAssignationNode typeAssignationNode, ValueAssignationNode valueAssignationNode)
        implements Node {

    @Override
    public Result<String> acceptChecker(Checker checker) {
        Result<String> interpretThis = checker.check(this);
        if (!interpretThis.isCorrect()) {
            return new IncorrectResult<>(interpretThis);
        }
        Result<String> interpretTypeAssignation = checker.check(this.typeAssignationNode());
        if (!interpretTypeAssignation.isCorrect()) {
            return new IncorrectResult<>(interpretTypeAssignation);
        }
        Result<String> interpretValueAssignation = checker.check(this.valueAssignationNode());
        if (!interpretValueAssignation.isCorrect()) {
            return new IncorrectResult<>(interpretValueAssignation);
        }
        return new CorrectResult<>("Successfully interpreted declaration");
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<String> interpretThis = interpreter.interpret(this);
        if (!interpretThis.isCorrect()) {
            return new IncorrectResult<>(interpretThis);
        }
        Result<String> interpretTypeAssignation = interpreter.interpret(this.typeAssignationNode());
        if (!interpretTypeAssignation.isCorrect()) {
            return new IncorrectResult<>(interpretTypeAssignation);
        }
        Result<String> interpretValueAssignation =
                interpreter.interpret(this.valueAssignationNode());
        if (!interpretValueAssignation.isCorrect()) {
            return new IncorrectResult<>(interpretValueAssignation);
        }
        return new CorrectResult<>("Successfully interpreted declaration");
    }

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
