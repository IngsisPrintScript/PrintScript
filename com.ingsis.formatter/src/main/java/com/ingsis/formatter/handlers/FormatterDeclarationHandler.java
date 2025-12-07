/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.type.types.Types;
import java.io.IOException;
import java.io.Writer;

public class FormatterDeclarationHandler implements NodeEventHandler<DeclarationKeywordNode> {
    private final Boolean hasPreAscriptionSpace;
    private final Boolean hasPostAscriptionSpace;
    private final Boolean isAssignationSpaced;
    private final NodeEventHandler<ExpressionNode> expressionHandler;
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterDeclarationHandler(
            Boolean hasPreAscriptionSpace,
            Boolean hasPostAscriptionSpace,
            Boolean isAssignationSpaced,
            NodeEventHandler<ExpressionNode> expressionHandler,
            ResultFactory resultFactory,
            Writer writer) {
        this.hasPreAscriptionSpace = hasPreAscriptionSpace;
        this.hasPostAscriptionSpace = hasPostAscriptionSpace;
        this.isAssignationSpaced = isAssignationSpaced;
        this.expressionHandler = expressionHandler;
        this.resultFactory = resultFactory;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(DeclarationKeywordNode node) {
        try {
            if (node.isMutable()) {
                writer.append("let ");
            } else {
                writer.append("const ");
            }
            writer.append(node.identifierNode().name());
            if (hasPreAscriptionSpace) {
                writer.append(" ");
            }
            writer.append(":");
            if (hasPostAscriptionSpace) {
                writer.append(" ");
            }
            writer.append(node.declaredType().keyword());
            if (isAssignationSpaced) {
                writer.append(" = ");
            } else {
                writer.append("=");
            }
            if (node.declaredType().equals(Types.STRING)) {
                writer.append("\"");
                Result<String> formatExpressionResult =
                        expressionHandler.handle(node.expressionNode());
                if (!formatExpressionResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatExpressionResult);
                }
                writer.append("\"");
            } else {
                Result<String> formatExpressionResult =
                        expressionHandler.handle(node.expressionNode());
                if (!formatExpressionResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatExpressionResult);
                }
            }
            writer.append(";");
        } catch (IOException e) {
            return resultFactory.createIncorrectResult(e.getMessage());
        }
        return resultFactory.createCorrectResult("Format passed.");
    }
}
