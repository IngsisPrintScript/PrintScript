/*
 * My Project
 */

package com.ingsis.syntactic.parsers.factories;

import com.ingsis.syntactic.factories.ParserChainFactory;
import com.ingsis.syntactic.parsers.TypeParser;
import com.ingsis.syntactic.parsers.conditional.ConditionalParser;
import com.ingsis.syntactic.parsers.declaration.DeclarationParser;
import com.ingsis.syntactic.parsers.expression.LineExpressionParser;
import com.ingsis.syntactic.parsers.function.CallFunctionParser;
import com.ingsis.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.syntactic.parsers.literal.LiteralParser;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.syntactic.parsers.operators.ValueAssignationParser;

public interface ParserFactory {
    CallFunctionParser createCallFunctionParser();

    DeclarationParser createDeclarationParser();

    ConditionalParser createConditionalParser(ParserChainFactory chainFactory);

    BinaryOperatorParser createBinaryOperatorParser();

    ValueAssignationParser createValueAssignationParser();

    TypeAssignationParser createTypeAssignationParser();

    TypeParser createTypeParser();

    IdentifierParser createIdentifierParser();

    LiteralParser createLiteralParser();

    LineExpressionParser createLineExpressionParser();
}
