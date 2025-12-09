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
            stream = (TokenStream) stream.next().nextIterator();
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
            writer.append(node.identifierNode().name());
            stream = (TokenStream) stream.next().nextIterator();
            if (hasPreAscriptionSpace) {
                writer.append(" ");
                stream = stream.consumeAll(space);
            } else {
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
            }
            writer.append(":");
            stream = (TokenStream) stream.next().nextIterator();
            if (hasPostAscriptionSpace) {
                writer.append(" ");
                stream = stream.consumeAll(space);
            } else {
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
            }
            stream = (TokenStream) stream.next().nextIterator();
            writer.append(node.declaredType().keyword());
            if (isAssignationUnspaced) {
                while (stream.consume(space).isCorrect()) {
                    stream = (TokenStream) stream.next().nextIterator();
                }
                writer.append("=");
                stream = (TokenStream) stream.next().nextIterator();
                while (stream.consume(space).isCorrect()) {
                    stream = (TokenStream) stream.next().nextIterator();
                }
            } else if (isAssignationSpaced) {
                writer.append(" = ");
                stream = stream.consumeAll(space);
                stream = (TokenStream) stream.next().nextIterator();
                stream = stream.consumeAll(space);
            } else {
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
                writer.append("=");
                stream = (TokenStream) stream.next().nextIterator();
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
            }
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
        } catch (IOException e) {
            return resultFactory.createIncorrectResult(e.getMessage());
        }
        return resultFactory.createCorrectResult("Format passed.");
    }
}
