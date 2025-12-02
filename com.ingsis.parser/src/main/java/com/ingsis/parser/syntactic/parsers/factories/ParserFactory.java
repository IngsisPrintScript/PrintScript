/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.factories;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.TypeParser;
import com.ingsis.parser.syntactic.parsers.conditional.ConditionalParser;
import com.ingsis.parser.syntactic.parsers.declaration.DeclarationParser;
import com.ingsis.parser.syntactic.parsers.expression.LineExpressionParser;
import com.ingsis.parser.syntactic.parsers.function.CallFunctionParser;
import com.ingsis.parser.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.parser.syntactic.parsers.literal.LiteralParser;
import com.ingsis.parser.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.parser.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.parser.syntactic.parsers.operators.ValueAssignationParser;
import com.ingsis.utils.nodes.nodes.Node;
import java.util.function.Supplier;

public interface ParserFactory {
    CallFunctionParser createCallFunctionParser();

    DeclarationParser createDeclarationParser();

    ConditionalParser createConditionalParser(Supplier<Parser<Node>> mainChainSupplier);

    BinaryOperatorParser createBinaryOperatorParser();

    ValueAssignationParser createValueAssignationParser();

    TypeAssignationParser createTypeAssignationParser();

    TypeParser createTypeParser();

    IdentifierParser createIdentifierParser();

    LiteralParser createLiteralParser();

    LineExpressionParser createLineExpressionParser();
}
