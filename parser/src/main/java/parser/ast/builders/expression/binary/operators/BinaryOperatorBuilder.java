package parser.ast.builders.expression.binary.operators;

import expression.binary.BinaryExpression;
import results.IncorrectResult;
import results.Result;
import parser.ast.builders.ASTreeBuilderInterface;
import stream.TokenStreamInterface;

import java.util.List;

public class BinaryOperatorBuilder implements ASTreeBuilderInterface {
    private final List<Class<? extends BinaryOperatorBuilder>> subclasses = List.of(AdditionOperatorBuilder.class, AssignationOperatorBuilder.class);

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        for (Class<? extends BinaryOperatorBuilder> subclass : subclasses) {
            try {
                BinaryOperatorBuilder subclassBuilder = subclass.getDeclaredConstructor().newInstance();
                if (subclassBuilder.canBuild(tokenStream)) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    @Override
    public Result<BinaryExpression> build(TokenStreamInterface tokenStream) {
        for (Class<? extends BinaryOperatorBuilder> subclass : subclasses) {
            try {
                BinaryOperatorBuilder subclassBuilder = subclass.getDeclaredConstructor().newInstance();
                if (subclassBuilder.canBuild(tokenStream)) {
                    return subclassBuilder.build(tokenStream);
                }
            } catch (Exception ignored) {
            }
        }
        return new IncorrectResult<>("There was no operator builder able to manage that operation.");
    }
}
