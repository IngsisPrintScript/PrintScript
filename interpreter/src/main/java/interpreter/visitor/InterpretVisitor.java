package interpreter.visitor;

import common.Environment;
import declaration.AscriptionNode;
import declaration.TypeNode;
import expression.ExpressionNode;

import expression.identifier.IdentifierNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;

public class InterpretVisitor implements visitor.InterpretVisitor {

    @Override
    public Result<String> interpret(LetStatementNode statement) {
        Result<AscriptionNode> getAscription = statement.ascription();
        if (!getAscription.isSuccessful()){
            return new IncorrectResult<>(getAscription.errorMessage());
        }
        AscriptionNode ascription = getAscription.result();
        Result<IdentifierNode> getIdentifier = ascription.identifier();
        Result<TypeNode> getType = ascription.type();
        if (!getIdentifier.isSuccessful()){
            return new IncorrectResult<>(getIdentifier.errorMessage());
        }
        if (!getType.isSuccessful()){
            return new IncorrectResult<>(getType.errorMessage());
        }
        IdentifierNode identifier = getIdentifier.result();
        TypeNode type = getType.result();
        Environment.getInstance().putIdType(identifier.name(), type.type());
        Result<ExpressionNode> getExpression = statement.expression();
        if (!getExpression.isSuccessful()){
            return new CorrectResult<>(
                    "Variable " +  identifier.name() + " was declared with type " + type.type() + "."
            );
        }
        ExpressionNode expression =  getExpression.result();
        Result<Object> evaluateExpression = expression.evaluate();
        if (!evaluateExpression.isSuccessful()){
            return new IncorrectResult<>(evaluateExpression.errorMessage());
        }
        Object result = evaluateExpression.result();
        Environment.getInstance().putIdValue(identifier.name(), result);
        return new CorrectResult<>(
                "Variable " +  identifier.name() + " was declared with type " + type.type()
                        + " and value " + result + "."
        );
    }

    @Override
    public Result<String> interpret(PrintStatementNode statement) {
        Result<ExpressionNode> getExpression = statement.expression();
        if (!getExpression.isSuccessful()){
            return new IncorrectResult<>(getExpression.errorMessage());
        }
        ExpressionNode expression = getExpression.result();
        Result<Object> evaluateExpression = expression.evaluate();
        if (!evaluateExpression.isSuccessful()){
            return new IncorrectResult<>(evaluateExpression.errorMessage());
        }
        Object result = evaluateExpression.result();
        System.out.println(result);
        return new CorrectResult<>(result + " was printed successfully.");
    }

    @Override
    public Result<String> interpret(ExpressionNode expression) {
        Result<Object> evaluateExpression = expression.evaluate();
        if (!evaluateExpression.isSuccessful()){
            return new IncorrectResult<>(evaluateExpression.errorMessage());
        }
        return new CorrectResult<>("Expression evaluated successfully.");
    }
}
