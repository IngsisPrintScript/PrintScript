package parser.ast.builders.expression.operators;

import common.responses.IncorrectResult;
import common.responses.Result;
import common.tokens.stream.TokenStreamInterface;
import parser.ast.builders.ASTreeBuilderInterface;

import java.util.List;

public class OperatorBuilder implements ASTreeBuilderInterface {
    private final List<Class<? extends OperatorBuilder>> subclasses = List.of(AdditionOperatorBuilder.class);

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        for (Class<? extends OperatorBuilder> subclass : subclasses) {
            try{
                OperatorBuilder subclassBuilder = subclass.getDeclaredConstructor().newInstance();
                if (subclassBuilder.canBuild(tokenStream)) {
                    return true;
                }
            } catch (Exception ignored){}
        }
        return false;
    }

    @Override
    public Result build(TokenStreamInterface tokenStream) {
        for (Class<? extends OperatorBuilder> subclass : subclasses) {
            try{
                OperatorBuilder subclassBuilder = subclass.getDeclaredConstructor().newInstance();
                if (subclassBuilder.canBuild(tokenStream)) {
                    return subclassBuilder.build(tokenStream);
                }
            } catch (Exception ignored){}
        }
        return new IncorrectResult("There was no operator builder able to manage that operation.");
    }
}
