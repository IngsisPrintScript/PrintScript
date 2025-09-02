package expression.binary;

import common.Environment;
import expression.ExpressionNode;
import expression.identifier.IdentifierNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import visitor.VisitorInterface;

public class AssignationNode extends BinaryExpression{

    public AssignationNode() {
        super();
    }

    @Override
    public Result<Object> evaluate() {
        Result<ExpressionNode> getLeftChild = this.getLeftChild();
        if (!getLeftChild.isSuccessful()){
            return new IncorrectResult<>(getLeftChild.errorMessage());
        }
        IdentifierNode identifier = (IdentifierNode) getLeftChild.result();

        Result<ExpressionNode> getRightChild = this.getRightChild();
        if (!getRightChild.isSuccessful()){
            return new IncorrectResult<>(getRightChild.errorMessage());
        }
        ExpressionNode rightChild = getRightChild.result();

        Result<Object> evaluateExpressionResult = rightChild.evaluate();
        if (!evaluateExpressionResult.isSuccessful()){
            return new IncorrectResult<>(evaluateExpressionResult.errorMessage());
        }
        Object result = evaluateExpressionResult.result();

        Environment.getInstance().putIdValue(
                identifier.name(),
                result
        );

        return new CorrectResult<>(result);
    }

    @Override
    public Result<String> prettyPrint() {
        Result<ExpressionNode>  getLeftChild = this.getLeftChild();
        if (!getLeftChild.isSuccessful()){
            return new IncorrectResult<>(getLeftChild().errorMessage());
        }
        ExpressionNode leftChild = getLeftChild.result();

        Result<ExpressionNode> getRightChild = this.getRightChild();
        if (!getRightChild.isSuccessful()){
            return new IncorrectResult<>(getRightChild.errorMessage());
        }
        ExpressionNode rightChild = getRightChild.result();

        String prettyPrint = leftChild.prettyPrint().result() + " = " + rightChild.prettyPrint().result();
        return new CorrectResult<>(prettyPrint);
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
