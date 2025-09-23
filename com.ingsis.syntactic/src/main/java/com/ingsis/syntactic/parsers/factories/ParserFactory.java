/*
 * My Project
 */

package com.ingsis.syntactic.parsers.factories;

import com.ingsis.syntactic.parsers.TypeParser;
import com.ingsis.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.syntactic.parsers.literal.LiteralParser;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.syntactic.parsers.operators.ValueAssignationParser;

public interface ParserFactory {
    BinaryOperatorParser createBinaryOperatorParser();

    ValueAssignationParser createValueAssignationParser();

    TypeAssignationParser createTypeAssignationParser();

    TypeParser createTypeParser();

    IdentifierParser createIdentifierParser();

    LiteralParser createLiteralParser();
}
