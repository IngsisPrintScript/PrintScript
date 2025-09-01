package expression.binary;

import common.Environment;
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
        if (!hasLeftChild()){
            return new IncorrectResult<>("Identifier not found to evaluate assignation.");
        }
        if (!hasRightChild()){
            return new IncorrectResult<>("Literal not found to evaluate assignation.");
        }

        if (!Environment.getInstance().variableIsDeclared((String) getLeftChild().result().evaluate().result())){
            return new IncorrectResult<>("Identifier not declared to evaluate assignation.");
        }

        Environment.getInstance().putIdValue(
                (String) getLeftChild().result().evaluate().result(),
                getRightChild().result().evaluate().result()
        );
        return new CorrectResult<>(getRightChild().result().evaluate().result());
    }

    @Override
    public Result<String> prettyPrint() {
        String prettyPrint = getLeftChild().result().evaluate().result() + " = " + getRightChild().result().evaluate().result();
        return new CorrectResult<>(prettyPrint);
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
