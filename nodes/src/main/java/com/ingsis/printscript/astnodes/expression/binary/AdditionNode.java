/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression.binary;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.VisitorInterface;

public class AdditionNode extends BinaryExpression {
    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public Result<Object> evaluate() {
        Result<ExpressionNode> getLeftChild = getLeftChild();
        if (!getLeftChild.isSuccessful()) {
            return new IncorrectResult<>(getLeftChild.errorMessage());
        }
        ExpressionNode leftChild = getLeftChild.result();

        Result<Object> evaluateLeftChild = leftChild.evaluate();
        if (!evaluateLeftChild.isSuccessful()) {
            return new IncorrectResult<>(evaluateLeftChild.errorMessage());
        }
        Object leftResult = evaluateLeftChild.result();

        Result<ExpressionNode> getRightChild = getRightChild();
        if (!getRightChild.isSuccessful()) {
            return new IncorrectResult<>(getLeftChild.errorMessage());
        }
        ExpressionNode rightChild = getRightChild.result();

        Result<Object> evaluateRightChild = rightChild.evaluate();
        if (!evaluateRightChild.isSuccessful()) {
            return new IncorrectResult<>(getRightChild.errorMessage());
        }
        Object rightResult = evaluateRightChild.result();

        try {
            Double leftNumber = Double.parseDouble((String) leftResult);
            Double rightNumber = Double.parseDouble((String) rightResult);
            return new CorrectResult<>(leftNumber + rightNumber);
        } catch (NumberFormatException ignore) {
        }

        try {
            String leftString = leftResult.toString();
            String rightString = rightResult.toString();
            return new CorrectResult<>(
                    leftString.replace("\"", "").replace("'", "")
                            + rightString.replace("\"", "").replace("'", ""));
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception ignored) {
        }

        return new IncorrectResult<>("Cannot add different types.");
    }

    @Override
    public Result<String> prettyPrint() {
        String leftChildResult = this.getLeftChild().result().prettyPrint().result();
        String rightChildResult = this.getRightChild().result().prettyPrint().result();
        return new CorrectResult<>(leftChildResult + " + " + rightChildResult);
    }
}
