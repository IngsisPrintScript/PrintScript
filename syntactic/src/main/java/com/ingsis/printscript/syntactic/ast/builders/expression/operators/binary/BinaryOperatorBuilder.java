/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression.operators.binary;

import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import java.util.List;

public class BinaryOperatorBuilder implements ASTreeBuilderInterface {
    private final List<Class<? extends BinaryOperatorBuilder>> BINARY_OPERATOR_BUILDERS;

    public BinaryOperatorBuilder() {
        BINARY_OPERATOR_BUILDERS =
                List.copyOf(
                        new ClassGraphReflectionsUtils()
                                .findSubclassesOf(BinaryOperatorBuilder.class)
                                .find());
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        for (Class<? extends BinaryOperatorBuilder> subclass : BINARY_OPERATOR_BUILDERS) {
            try {
                BinaryOperatorBuilder subclassBuilder =
                        subclass.getDeclaredConstructor().newInstance();
                if (subclassBuilder.canBuild(tokenStream)) {
                    return true;
                }
            } catch (RuntimeException exception) {
                throw exception;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    @Override
    public Result<BinaryExpression> build(TokenStreamInterface tokenStream) {
        for (Class<? extends BinaryOperatorBuilder> subclass : BINARY_OPERATOR_BUILDERS) {
            try {
                BinaryOperatorBuilder subclassBuilder =
                        subclass.getDeclaredConstructor().newInstance();
                if (subclassBuilder.canBuild(tokenStream)) {
                    return subclassBuilder.build(tokenStream);
                }
            } catch (RuntimeException exception) {
                throw exception;
            } catch (Exception ignored) {
            }
        }
        return new IncorrectResult<>(
                "There was no operator builder able to manage that operation.");
    }
}
