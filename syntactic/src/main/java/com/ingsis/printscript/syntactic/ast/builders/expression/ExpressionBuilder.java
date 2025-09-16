/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import java.util.List;

public class ExpressionBuilder implements ASTreeBuilderInterface {
    private final List<Class<? extends ExpressionBuilder>> EXPRESSION_BUILDERS;

    public ExpressionBuilder() {
        EXPRESSION_BUILDERS =
                List.of(
                        BinaryExpressionBuilder.class
                );
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        try {
            for (Class<? extends ExpressionBuilder> expressionBuilderClass : EXPRESSION_BUILDERS) {
                ExpressionBuilder builder =
                        expressionBuilderClass.getDeclaredConstructor().newInstance();
                if (builder.canBuild(tokenStream)) {
                    return true;
                }
            }
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception ignored) {
            return false;
        }
        return false;
    }

    @Override
    public Result<? extends ExpressionNode> build(TokenStreamInterface tokenStream) {
        try {
            for (Class<? extends ExpressionBuilder> expressionBuilderClass : EXPRESSION_BUILDERS) {
                ExpressionBuilder builder =
                        expressionBuilderClass.getDeclaredConstructor().newInstance();
                if (builder.canBuild(tokenStream)) {
                    Result<? extends ExpressionNode> builderResult = builder.build(tokenStream);
                    if (peekIsEOL(tokenStream)) {
                        tokenStream.consume();
                    }
                    return builderResult;
                }
            }
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            return new IncorrectResult<>(exception.getMessage());
        }
        return new IncorrectResult<>("That was not a valid expression.");
    }

    private boolean peekIsEOL(TokenStreamInterface tokenStream) {
        Result<TokenInterface> tokenResult = tokenStream.peek();
        if (!tokenResult.isSuccessful()) {
            return false;
        }
        return tokenResult.result().equals(new TokenFactory().createEndOfLineToken());
    }
}
