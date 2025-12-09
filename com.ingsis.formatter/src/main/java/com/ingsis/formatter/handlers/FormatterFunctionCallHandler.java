/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.atomic.literal.StringLiteralNode;
import com.ingsis.utils.nodes.expressions.function.CallFunctionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterFunctionCallHandler implements NodeEventHandler<ExpressionNode> {
    private final Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier;
    private final Boolean enforceSingleSeparation;
    private final TokenTemplate space;
    private final TokenTemplate newLine;
    private final ResultFactory resultFactory;
    private final Writer writer;

    public FormatterFunctionCallHandler(
            Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier,
            Boolean enforceSingleSeparation,
            TokenTemplate space,
            TokenTemplate newLine,
            ResultFactory resultFactory,
            Writer writer) {
        this.expressionHandlerSupplier = expressionHandlerSupplier;
        this.resultFactory = resultFactory;
        this.space = space;
        this.newLine = newLine;
        this.enforceSingleSeparation = enforceSingleSeparation;
        this.writer = writer;
    }

    @Override
    public Result<String> handle(ExpressionNode node) {
        if (!(node instanceof CallFunctionNode callFunctionNode)) {
            return resultFactory.createIncorrectResult("Incorrect handler.");
        }
        TokenStream stream = node.stream();
        try {
            writer.append(callFunctionNode.symbol());
            stream = consumeSpacesWithEnforcement(stream);
            writer.append("(");
            stream = consumeSpacesWithEnforcement(stream);
            Result<String> parseExpressionResult = formatArguments(callFunctionNode, stream);
            if (!parseExpressionResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(parseExpressionResult);
            }
            stream = consumeSpacesWithEnforcement(stream);
            writer.append(")");
            writer.append(";");
        } catch (IOException e) {
            return resultFactory.createIncorrectResult(e.getMessage());
        }
        return resultFactory.createCorrectResult("Format correct.");
    }

    private Result<String> formatArguments(CallFunctionNode node, TokenStream stream) throws IOException {
        boolean needsComa = false;
        for (ExpressionNode child : node.children().subList(1, node.children().size())) {
            if (needsComa) {
                stream = consumeSpacesWithEnforcement(stream);
                writer.append(",");
                stream = consumeSpacesWithEnforcement(stream);
            }
            if (child instanceof StringLiteralNode){
                writer.append("\"");
                Result<String> formatChildResult = expressionHandlerSupplier.get().handle(child);
                if (!formatChildResult.isCorrect()) {
                    return resultFactory.cloneIncorrectResult(formatChildResult);
                }
                needsComa = true;
                writer.append("\"");
            } else {
            Result<String> formatChildResult = expressionHandlerSupplier.get().handle(child);
            if (!formatChildResult.isCorrect()) {
                return resultFactory.cloneIncorrectResult(formatChildResult);
            }
            needsComa = true;
            }
        }
        SafeIterationResult<Token> consumeNewLine = stream.consume(newLine);
        if (consumeNewLine.isCorrect()){
            writer.append("\n");
        }
        return resultFactory.createCorrectResult("Arguments formatted correctly.");
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
}
