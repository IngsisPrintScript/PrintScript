package nodes.expression.binary;


import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import nodes.visitor.VisitorInterface;

public class AdditionNode extends BinaryExpression{
    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public Result<Object> evaluate() {
        Object leftChildResult = this.getLeftChild().result().evaluate().result();
        Object rightChildResult = this.getRightChild().result().evaluate().result();
        if (leftChildResult instanceof String lString &&  rightChildResult instanceof String rString) {
            return new CorrectResult<>(lString + rString);
        } else if (leftChildResult instanceof Number lNumber && rightChildResult instanceof Number rNumber) {
            return new CorrectResult<>(lNumber.floatValue() + rNumber.floatValue());
        } else {
            return new IncorrectResult<>("Cannot add different types.");
        }
    }

    @Override
    public Result<String> prettyPrint() {
        String  leftChildResult = this.getLeftChild().result().prettyPrint().result();
        String  rightChildResult = this.getRightChild().result().prettyPrint().result();
        return new CorrectResult<>(leftChildResult + " + " + rightChildResult);
    }
}
