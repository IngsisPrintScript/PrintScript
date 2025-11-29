/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operators;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.parser.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.parser.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class ValueAssignationParser implements Parser<ValueAssignationNode> {
    private final Token VALUE_ASSIGNATION_TEMPLATE;
    private final NodeFactory NODE_FACTORY;
    private final IdentifierParser IDENTIFIER_PARSER;
    private final BinaryOperatorParser OPERATOR_PARSER;

    public ValueAssignationParser(
            ParserFactory PARSER_FACTORY, TokenFactory TOKEN_FACTORY, NodeFactory NODE_FACTORY) {
        this.VALUE_ASSIGNATION_TEMPLATE = TOKEN_FACTORY.createOperatorToken("=");
        this.NODE_FACTORY = NODE_FACTORY;
        this.IDENTIFIER_PARSER = PARSER_FACTORY.createIdentifierParser();
        this.OPERATOR_PARSER = PARSER_FACTORY.createBinaryOperatorParser();
    }

    @Override
    public Result<ValueAssignationNode> parse(TokenStream stream) {
        Result<IdentifierNode> parseIdentifierResult = IDENTIFIER_PARSER.parse(stream);
        if (!parseIdentifierResult.isCorrect()) {
            return new IncorrectResult<>(parseIdentifierResult);
        }
        IdentifierNode identifierNode = parseIdentifierResult.result();

        Result<Token> consumeValueAssignationResult = stream.consume(VALUE_ASSIGNATION_TEMPLATE);
        if (!consumeValueAssignationResult.isCorrect()) {
            return new IncorrectResult<>(consumeValueAssignationResult);
        }

        Result<ExpressionNode> parseTypeResult = OPERATOR_PARSER.parse(stream);
        if (!parseTypeResult.isCorrect()) {
            return new IncorrectResult<>(parseTypeResult);
        }
        ExpressionNode expressionNode = parseTypeResult.result();

        Token valueAssignation = consumeValueAssignationResult.result();
        return new CorrectResult<>(
                NODE_FACTORY.createValueAssignationNode(
                        identifierNode,
                        expressionNode,
                        valueAssignation.line(),
                        valueAssignation.column()));
    }
}
