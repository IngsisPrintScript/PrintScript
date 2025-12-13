/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.token.template.TokenTemplate;
import java.io.Writer;

public class FormatterDeclarationHandler implements NodeEventHandler<DeclarationKeywordNode> {
    private final Boolean hasPreAscriptionSpace;
    private final Boolean hasPostAscriptionSpace;
    private final Boolean isAssignationUnspaced;
    private final Boolean isAssignationSpaced;
    private final Boolean enforceSingleSeparation;
    private final NodeEventHandler<ExpressionNode> expressionHandler;
    private final ResultFactory resultFactory;
    private final Writer writer;
    private final TokenTemplate space;

    public FormatterDeclarationHandler(
            Boolean hasPreAscriptionSpace,
            Boolean hasPostAscriptionSpace,
            Boolean isAssignationUnspaced,
            Boolean isAssignationSpaced,
            Boolean enforceSingleSeparation,
            NodeEventHandler<ExpressionNode> expressionHandler,
            ResultFactory resultFactory,
            Writer writer,
            TokenTemplate space) {
        this.hasPreAscriptionSpace = hasPreAscriptionSpace;
        this.hasPostAscriptionSpace = hasPostAscriptionSpace;
        this.isAssignationUnspaced = isAssignationUnspaced;
        this.isAssignationSpaced = isAssignationSpaced;
        this.enforceSingleSeparation = enforceSingleSeparation;
        this.expressionHandler = expressionHandler;
        this.resultFactory = resultFactory;
        this.writer = writer;
        this.space = space;
    }

    @Override
    public Result<String> handle(DeclarationKeywordNode node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
}
