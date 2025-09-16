/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression.binary.v1;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.VisitorInterface;

public class DoubleEqualNode extends BinaryExpression {

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
            return new IncorrectResult<>(getRightChild.errorMessage());
        }
        ExpressionNode rightChild = getRightChild.result();

        Result<Object> evaluateRightChild = rightChild.evaluate();
        if (!evaluateRightChild.isSuccessful()) {
            return new IncorrectResult<>(evaluateRightChild.errorMessage());
        }
        Object rightResult = evaluateRightChild.result();
        try {
            Double leftNumber = Double.parseDouble(leftResult.toString());
            Double rightNumber = Double.parseDouble(rightResult.toString());
            return new CorrectResult<>(leftNumber.equals(rightNumber));
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception ignored) {
        }

        if (leftResult instanceof Boolean && rightResult instanceof Boolean) {
            return new CorrectResult<>(leftResult.equals(rightResult));
        }

        if (leftResult != null && rightResult != null) {
            return new CorrectResult<>(
                    leftResult
                            .toString()
                            .replace("\"", "")
                            .replace("'", "")
                            .equals(rightResult.toString().replace("\"", "").replace("'", "")));
        }

        return new IncorrectResult<>("Cannot compare different types.");
    }

    @Override
    public Result<String> prettyPrint() {
        String leftChildResult = this.getLeftChild().result().prettyPrint().result();
        String rightChildResult = this.getRightChild().result().prettyPrint().result();
        return new CorrectResult<>(leftChildResult + " == " + rightChildResult);
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
