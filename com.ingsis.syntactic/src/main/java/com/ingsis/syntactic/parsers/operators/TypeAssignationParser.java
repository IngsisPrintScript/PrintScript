/*
 * My Project
 */

package com.ingsis.syntactic.parsers.operators;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.TypeParser;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;

public final class TypeAssignationParser implements Parser {
    private final Token TYPE_ASSIGNATION_TEMPLATE;
    private final NodeFactory NODE_FACTORY;
    private final IdentifierParser IDENTIFIER_PARSER;
    private final TypeParser TYPE_PARSER;

    public TypeAssignationParser(
            ParserFactory PARSER_FACTORY, TokenFactory TOKEN_FACTORY, NodeFactory NODE_FACTORY) {
        this.TYPE_ASSIGNATION_TEMPLATE = TOKEN_FACTORY.createOperatorToken(":");
        this.NODE_FACTORY = NODE_FACTORY;
        this.IDENTIFIER_PARSER = PARSER_FACTORY.createIdentifierParser();
        this.TYPE_PARSER = PARSER_FACTORY.createTypeParser();
    }

    @Override
    public Result<TypeAssignationNode> parse(TokenStream stream) {
        Result<IdentifierNode> parseIdentifierResult = IDENTIFIER_PARSER.parse(stream);
        if (!parseIdentifierResult.isCorrect()) {
            return new IncorrectResult<>(parseIdentifierResult);
        }
        IdentifierNode identifierNode = parseIdentifierResult.result();

        Result<Token> consumeTypeAssignationResult = stream.consume(TYPE_ASSIGNATION_TEMPLATE);
        if (!consumeTypeAssignationResult.isCorrect()) {
            return new IncorrectResult<>(consumeTypeAssignationResult);
        }

        Result<TypeNode> parseTypeResult = TYPE_PARSER.parse(stream);
        if (!parseTypeResult.isCorrect()) {
            return new IncorrectResult<>(parseTypeResult);
        }
        TypeNode typeNode = parseTypeResult.result();

        return new CorrectResult<>(
                NODE_FACTORY.createTypeAssignationNode(identifierNode, typeNode));
    }
}
