/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.expression;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.FinalBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.binary.BinaryExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.identifier.IdentifierBuilder;
import com.ingsis.printscript.syntactic.ast.builders.literal.LiteralBuilder;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import java.util.List;

public class ExpressionBuilder implements ASTreeBuilderInterface {
    private final List<Class<? extends ExpressionBuilder>> EXPRESSION_BUILDERS =
            List.of(BinaryExpressionBuilder.class, LiteralBuilder.class, IdentifierBuilder.class);
    private final ASTreeBuilderInterface NEXT_BUILDER;

    public ExpressionBuilder(ASTreeBuilderInterface nextBuilder) {
        this.NEXT_BUILDER = nextBuilder;
    }

    public ExpressionBuilder() {
        this.NEXT_BUILDER = new FinalBuilder();
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
        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    @Override
    public Result<ExpressionNode> build(TokenStreamInterface tokenStream) {
        try {
            for (Class<? extends ExpressionBuilder> expressionBuilderClass : EXPRESSION_BUILDERS) {
                ExpressionBuilder builder =
                        expressionBuilderClass.getDeclaredConstructor().newInstance();
                if (builder.canBuild(tokenStream)) {
                    return builder.build(tokenStream);
                }
            }
        } catch (Exception exception) {
            return new IncorrectResult<>(exception.getMessage());
        }
        return new IncorrectResult<>("That was not a valid expression.");
    }
}
