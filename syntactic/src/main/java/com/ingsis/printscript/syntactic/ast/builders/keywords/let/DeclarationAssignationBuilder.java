/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.keywords.let;

import com.ingsis.printscript.astnodes.declaration.AscriptionNode;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ascription.AscriptionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.function.FunctionCallBuilder;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactory;
import com.ingsis.printscript.syntactic.factories.AstBuilderFactoryInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public final class DeclarationAssignationBuilder extends LetBuilder {
    private final TokenInterface ASSIGNATION_TOKEN_TEMPLATE;
    private final AscriptionBuilder ASCRIPTION_BUILDER;
    private final FunctionCallBuilder CALL_FUNCTION_BUILDER;
    private final ExpressionBuilder EXPRESSION_BUILDER;

    public DeclarationAssignationBuilder() {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        ASSIGNATION_TOKEN_TEMPLATE = tokenFactory.createAssignationToken();
        AstBuilderFactoryInterface builderFactory = new AstBuilderFactory();
        CALL_FUNCTION_BUILDER = (FunctionCallBuilder) builderFactory.createCallFunctionBuilder();
        ASCRIPTION_BUILDER = (AscriptionBuilder) builderFactory.createAscriptionBuilder();
        EXPRESSION_BUILDER = (ExpressionBuilder) builderFactory.createExpressionBuilder();
    }

    @Override
    public Result<LetStatementNode> build(TokenStreamInterface tokenStream) {
        Result<AscriptionNode> buildAscritionResult = ASCRIPTION_BUILDER.build(tokenStream);
        if (!buildAscritionResult.isSuccessful()) {
            return new IncorrectResult<>("Cannot build let ast.");
        }

        LetStatementNode letNode = (LetStatementNode) new NodeFactory().createLetStatementNode();
        letNode.setAscription(buildAscritionResult.result());

        if (!tokenStream.consume(ASSIGNATION_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("Stream is not a Declaration and Assignation.");
        }

        Result<CallFunctionNode> buildCallFunctionResult = CALL_FUNCTION_BUILDER.build(tokenStream);
        if (!buildCallFunctionResult.isSuccessful()) {
            Result<? extends ExpressionNode> buildLiteralResult = EXPRESSION_BUILDER.build(tokenStream);
            if (!buildLiteralResult.isSuccessful()) {
                return new IncorrectResult<>(buildLiteralResult.errorMessage());
            }
            ExpressionNode expressionNode = buildLiteralResult.result();
            letNode.setExpression(expressionNode);
            return new CorrectResult<>(letNode);
        }

        CallFunctionNode callFunctionNode = buildCallFunctionResult.result();

        letNode.setExpression(callFunctionNode);
        return new CorrectResult<>(letNode);
    }
}
