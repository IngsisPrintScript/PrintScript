/*
 * My Project
 */

package com.ingsis.syntactic.parsers.factories;

import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.syntactic.parsers.DefaultParserRegistry;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.ParserRegistry;
import com.ingsis.syntactic.parsers.TypeParser;
import com.ingsis.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.syntactic.parsers.literal.LiteralParser;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.syntactic.parsers.operators.ValueAssignationParser;
import com.ingsis.tokens.factories.TokenFactory;

public final class DefaultParserFactory implements ParserFactory {
    private final TokenFactory TOKEN_FACTORY;
    private final NodeFactory NODE_FACTORY;

    public DefaultParserFactory(TokenFactory tokenFactory, NodeFactory nodeFactory) {
        this.TOKEN_FACTORY = tokenFactory;
        this.NODE_FACTORY = nodeFactory;
    }

    @Override
    public BinaryOperatorParser createBinaryOperatorParser() {
        return new BinaryOperatorParser(NODE_FACTORY, TOKEN_FACTORY, binaryLeafOperators());
    }

    private Parser binaryLeafOperators() {
        ParserRegistry registry = new DefaultParserRegistry();
        registry.registerTokenizer(createIdentifierParser());
        registry.registerTokenizer(createLiteralParser());
        return registry;
    }

    @Override
    public ValueAssignationParser createValueAssignationParser() {
        return new ValueAssignationParser(this, TOKEN_FACTORY, NODE_FACTORY);
    }

    @Override
    public TypeAssignationParser createTypeAssignationParser() {
        return new TypeAssignationParser(this, TOKEN_FACTORY, NODE_FACTORY);
    }

    @Override
    public TypeParser createTypeParser() {
        return new TypeParser(NODE_FACTORY, TOKEN_FACTORY);
    }

    @Override
    public IdentifierParser createIdentifierParser() {
        return new IdentifierParser(TOKEN_FACTORY, NODE_FACTORY);
    }

    @Override
    public LiteralParser createLiteralParser() {
        return new LiteralParser(TOKEN_FACTORY, NODE_FACTORY);
    }
}
