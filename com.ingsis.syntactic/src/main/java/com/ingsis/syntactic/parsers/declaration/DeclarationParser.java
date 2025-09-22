/*
 * My Project
 */

package com.ingsis.syntactic.parsers.declaration;

import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.nodes.operator.TypeAssignationNode;
import com.ingsis.nodes.operator.ValueAssignationNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.factories.ParserFactory;
import com.ingsis.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.syntactic.parsers.operators.ValueAssignationParser;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;

public final class DeclarationParser implements Parser {
    private final Token LET_TOKEN_TEMPLATE;
    private final TypeAssignationParser TYPE_ASSIGNATION_PARSER;
    private final ValueAssignationParser VALUE_ASSIGNATION_PARSER;
    private final NodeFactory NODE_FACTORY;

    public DeclarationParser(
            TokenFactory TOKEN_FACTORY, ParserFactory PARSER_FACTORY, NodeFactory NODE_FACTORY) {
        this.LET_TOKEN_TEMPLATE = TOKEN_FACTORY.createKeywordToken("let");
        this.TYPE_ASSIGNATION_PARSER = PARSER_FACTORY.createTypeAssignationParser();
        this.VALUE_ASSIGNATION_PARSER = PARSER_FACTORY.createValueAssignationParser();
        this.NODE_FACTORY = NODE_FACTORY;
    }

    private boolean canParse(TokenStream stream) {
        if (!stream.hasNext()) {
            return false;
        }
        Token token = stream.peek();
        return token.equals(LET_TOKEN_TEMPLATE);
    }

    @Override
    public Result<Checkable> parse(TokenStream stream) {
        Result<Token> consumeLetResult = stream.consume(LET_TOKEN_TEMPLATE);
        if (!consumeLetResult.isCorrect()) {
            return new IncorrectResult<>(consumeLetResult);
        }

        Result<TypeAssignationNode> parseTypeAssignationResult =
                TYPE_ASSIGNATION_PARSER.parse(stream);
        if (!parseTypeAssignationResult.isCorrect()) {
            return new IncorrectResult<>(parseTypeAssignationResult);
        }
        TypeAssignationNode typeAssignationNode = parseTypeAssignationResult.result();

        Result<ValueAssignationNode> parseValueAssignationResult =
                VALUE_ASSIGNATION_PARSER.parse(stream);
        if (!parseValueAssignationResult.isCorrect()) {
            return new IncorrectResult<>(parseValueAssignationResult);
        }
        ValueAssignationNode valueAssignationNode = parseValueAssignationResult.result();

        return new CorrectResult<>(
                NODE_FACTORY.createLetNode(typeAssignationNode, valueAssignationNode));
    }
}
