/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.operators;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.TypeParser;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.parser.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public final class TypeAssignationParser implements Parser<TypeAssignationNode> {
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

        Token typeAssignation = consumeTypeAssignationResult.result();
        return new CorrectResult<>(
                NODE_FACTORY.createTypeAssignationNode(
                        identifierNode,
                        typeNode,
                        typeAssignation.line(),
                        typeAssignation.column()));
    }
}
