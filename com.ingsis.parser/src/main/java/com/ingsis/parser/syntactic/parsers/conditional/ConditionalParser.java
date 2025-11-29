/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.conditional;

import com.ingsis.parser.syntactic.factories.ParserChainFactory;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.ArrayList;
import java.util.List;

public final class ConditionalParser implements Parser<IfKeywordNode> {
    private final Token IF_TEMPLATE;
    private final Token ELSE_TEMPLATE;
    private final Token LEFT_PARENTHESIS_TEMPLATE;
    private final Token RIGHT_PARENTHESIS_TEMPLATE;
    private final Token LEFT_BRACE_TEMPLATE;
    private final Token RIGHT_BRACE_TEMPLATE;
    private final Parser<ExpressionNode> EXPRESSION_PARSER;
    private final ParserChainFactory CHAIN_FACTORY;
    private final NodeFactory NODE_FACTORY;

    public ConditionalParser(
            TokenFactory TOKEN_FACTORY,
            ParserFactory PARSER_FACTORY,
            NodeFactory nodeFactory,
            ParserChainFactory CHAIN_FACTORY) {
        this.IF_TEMPLATE = TOKEN_FACTORY.createKeywordToken("if");
        this.ELSE_TEMPLATE = TOKEN_FACTORY.createKeywordToken("else");
        this.LEFT_PARENTHESIS_TEMPLATE = TOKEN_FACTORY.createSeparatorToken("(");
        this.RIGHT_PARENTHESIS_TEMPLATE = TOKEN_FACTORY.createSeparatorToken(")");
        this.LEFT_BRACE_TEMPLATE = TOKEN_FACTORY.createSeparatorToken("{");
        this.RIGHT_BRACE_TEMPLATE = TOKEN_FACTORY.createSeparatorToken("}");
        this.EXPRESSION_PARSER = PARSER_FACTORY.createBinaryOperatorParser();
        this.CHAIN_FACTORY = CHAIN_FACTORY;
        this.NODE_FACTORY = nodeFactory;
    }

    @Override
    public Result<IfKeywordNode> parse(TokenStream stream) {
        Result<Token> consumeIfKeywordResult = stream.consume(IF_TEMPLATE);
        if (!consumeIfKeywordResult.isCorrect()) {
            return new IncorrectResult<>("Stream was not a conditional");
        }
        Result<ExpressionNode> parseConditionResult = parseCondition(stream);
        if (!parseConditionResult.isCorrect()) {
            return new IncorrectResult<>(parseConditionResult);
        }

        Result<List<Node>> parseThenBodyResult = parseBody(stream);
        if (!parseThenBodyResult.isCorrect()) {
            return new IncorrectResult<>(parseThenBodyResult);
        }
        Token ifToken = consumeIfKeywordResult.result();
        if (!stream.consume(ELSE_TEMPLATE).isCorrect()) {
            return new CorrectResult<>(
                    NODE_FACTORY.createConditionalNode(
                            parseConditionResult.result(),
                            parseThenBodyResult.result(),
                            List.of(),
                            ifToken.line(),
                            ifToken.column()));
        }

        Result<List<Node>> parseElseBodyResult = parseBody(stream);
        if (!parseElseBodyResult.isCorrect()) {
            return new IncorrectResult<>(parseElseBodyResult);
        }

        return new CorrectResult<>(
                NODE_FACTORY.createConditionalNode(
                        parseConditionResult.result(),
                        parseThenBodyResult.result(),
                        parseElseBodyResult.result(),
                        ifToken.line(),
                        ifToken.column()));
    }

    private Result<ExpressionNode> parseCondition(TokenStream stream) {
        if (!stream.consume(LEFT_PARENTHESIS_TEMPLATE).isCorrect()) {
            return new IncorrectResult<>("Error on condition syntax.");
        }
        Result<ExpressionNode> parseConditionResult = EXPRESSION_PARSER.parse(stream);
        if (!stream.consume(RIGHT_PARENTHESIS_TEMPLATE).isCorrect()) {
            return new IncorrectResult<>("Error on condition syntax.");
        }
        return parseConditionResult;
    }

    private Result<List<Node>> parseBody(TokenStream stream) {
        if (!stream.consume(LEFT_BRACE_TEMPLATE).isCorrect()) {
            return new IncorrectResult<>("Error on then body syntax.");
        }
        List<Node> body = new ArrayList<>();
        while (!stream.consume(RIGHT_BRACE_TEMPLATE).isCorrect()) {
            Result<Node> parseBodyItemResult = CHAIN_FACTORY.createDefaultChain().parse(stream);
            if (!parseBodyItemResult.isCorrect()) {
                return new IncorrectResult<>(parseBodyItemResult);
            }
            body.add(parseBodyItemResult.result());
        }
        return new CorrectResult<List<Node>>(body);
    }
}
