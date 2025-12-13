/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public class DeclarationParser implements Parser<Node> {
    private final List<TokenTemplate> declarations;
    private final TokenTemplate colon;
    private final List<TokenTemplate> types;
    private final TokenTemplate equals;
    private final TokenTemplate semicolon;
    private final Parser<ExpressionNode> identifierParser;
    private final Parser<ExpressionNode> expressionParser;
    private final NodeFactory nodeFactory;
    private final NodePriority nodePriority;

    public DeclarationParser(
            List<TokenTemplate> declarations,
            TokenTemplate colon,
            List<TokenTemplate> types,
            TokenTemplate equals,
            TokenTemplate semicolon,
            Parser<ExpressionNode> identifierParser,
            Parser<ExpressionNode> expressionParser,
            NodeFactory nodeFactory) {
        this.declarations = declarations;
        this.types = types;
        this.colon = colon;
        this.equals = equals;
        this.semicolon = semicolon;
        this.identifierParser = identifierParser;
        this.expressionParser = expressionParser;
        this.nodeFactory = nodeFactory;
        this.nodePriority = NodePriority.STATEMENT;
    }

    @Override
    public ParseResult<Node> parse(TokenStream stream) {
        for (TokenTemplate declaration : declarations) {
            switch (stream.consume(declaration)) {
                case ConsumeResult.CORRECT C -> {
                    return parseIdentifier(C);
                }
                default -> {}
            }
        }
        return new ParseResult.INVALID<>();
    }

    private ParseResult<Node> parseIdentifier(ConsumeResult.CORRECT in) {
        return switch (identifierParser.parse(in.finalTokenStream())) {
            case ParseResult.COMPLETE<ExpressionNode> C -> parseColon(in.consumedToken(), C);
            default -> new ParseResult.PREFIX<>(nodePriority);
        };
    }

    private ParseResult<Node> parseColon(
            Token declaration, ParseResult.COMPLETE<ExpressionNode> in) {
        return switch (in.finalStream().consume(colon)) {
            case ConsumeResult.INCORRECT I -> new ParseResult.PREFIX<>(nodePriority);
            case ConsumeResult.CORRECT C -> {
                if (!(in.node() instanceof IdentifierNode identifierNode)) {
                    yield new ParseResult.PREFIX<>(nodePriority);
                }

                yield parseType(declaration, identifierNode, C);
            }
        };
    }

    private ParseResult<Node> parseType(
            Token declaration, IdentifierNode identifier, ConsumeResult.CORRECT in) {
        for (TokenTemplate type : types) {
            switch (in.finalTokenStream().consume(type)) {
                case ConsumeResult.CORRECT C -> {
                    return parseEquals(declaration, identifier, C);
                }
                default -> {}
            }
        }
        return new ParseResult.PREFIX<>(nodePriority);
    }

    private ParseResult<Node> parseEquals(
            Token declaration, IdentifierNode identifier, ConsumeResult.CORRECT in) {
        return switch (in.finalTokenStream().consume(equals)) {
            case ConsumeResult.CORRECT C ->
                    parseExpression(declaration, identifier, in.consumedToken().value(), C);
            case ConsumeResult.INCORRECT I -> new ParseResult.PREFIX<>(nodePriority);
        };
    }

    private ParseResult<Node> parseExpression(
            Token declaration, IdentifierNode identifier, String type, ConsumeResult.CORRECT in) {
        return switch (expressionParser.parse(in.finalTokenStream())) {
            case ParseResult.COMPLETE<ExpressionNode> C ->
                    parseSemiColon(declaration, identifier, type, C);
            default -> new ParseResult.PREFIX<>(nodePriority);
        };
    }

    private ParseResult<Node> parseSemiColon(
            Token declaration,
            IdentifierNode identifier,
            String type,
            ParseResult.COMPLETE<ExpressionNode> in) {
        return switch (in.finalStream().consume(semicolon)) {
            case ConsumeResult.CORRECT C -> createNode(declaration, identifier, type, in.node(), C);
            case ConsumeResult.INCORRECT I -> new ParseResult.PREFIX<>(nodePriority);
        };
    }

    private ParseResult<Node> createNode(
            Token declaration,
            IdentifierNode identifier,
            String type,
            ExpressionNode expression,
            ConsumeResult.CORRECT in) {
        return new ParseResult.COMPLETE<Node>(
                nodeFactory.createDeclarationNode(
                        identifier,
                        expression,
                        Types.fromKeyword(type),
                        declaration.value().equals("let"),
                        declaration.line(),
                        declaration.column()),
                in.finalTokenStream(),
                nodePriority,
                true);
    }
}
