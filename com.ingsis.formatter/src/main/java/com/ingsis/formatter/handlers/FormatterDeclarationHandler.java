/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.TokenStream;
import com.ingsis.utils.type.types.Types;
import java.io.IOException;
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
        TokenStream stream = node.stream();
        try {
            if (node.isMutable()) {
                writer.append("let");
            } else {
                writer.append("const");
            }
            stream = consumeSpacesWithEnforcement((TokenStream) stream.next().nextIterator());
            writer.append(node.identifierNode().name());
            stream = handlePreAscriptionSpacing((TokenStream) stream.next().nextIterator());
            writer.append(":");
            stream = handlePostAscriptionSpacing((TokenStream) stream.next().nextIterator());
            writer.append(node.declaredType().keyword());
            stream = handleAssignationSpacing((TokenStream) stream.next().nextIterator());
            if (node.declaredType().equals(Types.STRING)) {
                writer.append("\"");
                stream = (TokenStream) stream.next().nextIterator();
                Result<String> formatExpressionResult =
                        expressionHandler.handle(node.expressionNode());
                stream.advanceBy(node.expressionNode().stream().consumeAll());
                if (!formatExpressionResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatExpressionResult);
                }
                writer.append("\"");
                stream = (TokenStream) stream.next().nextIterator();
            } else {
                Result<String> formatExpressionResult =
                        expressionHandler.handle(node.expressionNode());
                stream = stream.advanceBy(node.expressionNode().stream().consumeAll());
                if (!formatExpressionResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatExpressionResult);
                }
            }
            writer.append(";");
            SafeIterationResult<Token> getNewLineStream = stream.next();
            if (getNewLineStream.isCorrect()) {
                writer.append("\n");
            }
        } catch (IOException e) {
            return resultFactory.createIncorrectResult(e.getMessage());
        }
        return resultFactory.createCorrectResult("Format passed.");
    }

    private TokenStream consumeSpacesWithEnforcement(TokenStream stream) throws IOException {
        if (enforceSingleSeparation) {
            writer.append(" ");
            while (stream.consume(space).isCorrect()) {
                stream = (TokenStream) stream.next().nextIterator();
            }
        } else {
            while (stream.consume(space).isCorrect()) {
                writer.append(" ");
                stream = (TokenStream) stream.next().nextIterator();
            }
        }
        return stream;
    }

    private TokenStream addSpaceAndConsumeAll(TokenStream stream) throws IOException {
        writer.append(" ");
        return stream.consumeAll(space);
    }

    private TokenStream handlePreAscriptionSpacing(TokenStream stream) throws IOException {
        if (hasPreAscriptionSpace) {
            return addSpaceAndConsumeAll(stream);
        } else {
            return consumeSpacesWithEnforcement(stream);
        }
    }

    private TokenStream handlePostAscriptionSpacing(TokenStream stream) throws IOException {
        if (hasPostAscriptionSpace) {
            return addSpaceAndConsumeAll(stream);
        } else {
            return consumeSpacesWithEnforcement(stream);
        }
    }

    private TokenStream handleAssignationSpacing(TokenStream stream) throws IOException {
        if (isAssignationUnspaced) {
            stream = consumeSpacesWithoutWriting(stream);
            writer.append("=");
            stream = (TokenStream) stream.next().nextIterator();
            return consumeSpacesWithoutWriting(stream);
        } else if (isAssignationSpaced) {
            writer.append(" = ");
            stream = stream.consumeAll(space);
            stream = (TokenStream) stream.next().nextIterator();
            return stream.consumeAll(space);
        } else {
            stream = consumeSpacesWithEnforcement(stream);
            writer.append("=");
            stream = (TokenStream) stream.next().nextIterator();
            return consumeSpacesWithEnforcement(stream);
        }
    }

    private TokenStream consumeSpacesWithoutWriting(TokenStream stream) {
        while (stream.consume(space).isCorrect()) {
            stream = (TokenStream) stream.next().nextIterator();
        }
        return stream;
    }
}
