/*
 * My Project
 */

package com.ingsis.syntactic.factories;

import static org.junit.jupiter.api.Assertions.*;

import com.ingsis.nodes.factories.DefaultNodeFactory;
import com.ingsis.nodes.factories.NodeFactory;
import com.ingsis.syntactic.parsers.TypeParser;
import com.ingsis.syntactic.parsers.declaration.DeclarationParser;
import com.ingsis.syntactic.parsers.expression.LineExpressionParser;
import com.ingsis.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.syntactic.parsers.function.CallFunctionParser;
import com.ingsis.syntactic.parsers.identifier.IdentifierParser;
import com.ingsis.syntactic.parsers.literal.LiteralParser;
import com.ingsis.syntactic.parsers.operator.BinaryOperatorParser;
import com.ingsis.syntactic.parsers.operators.TypeAssignationParser;
import com.ingsis.syntactic.parsers.operators.ValueAssignationParser;
import com.ingsis.tokens.factories.DefaultTokensFactory;
import com.ingsis.tokens.factories.TokenFactory;
import org.junit.jupiter.api.Test;

class DefaultParserFactoryTest {

    @Test
    void factoryCreatesAllParsers() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();

        DefaultParserFactory factory = new DefaultParserFactory(tf, nf);

        DeclarationParser decl = factory.createDeclarationParser();
        assertNotNull(decl);

        BinaryOperatorParser bin = factory.createBinaryOperatorParser();
        assertNotNull(bin);

        ValueAssignationParser val = factory.createValueAssignationParser();
        assertNotNull(val);

        TypeAssignationParser typeAssign = factory.createTypeAssignationParser();
        assertNotNull(typeAssign);

        TypeParser tp = factory.createTypeParser();
        assertNotNull(tp);

        IdentifierParser idp = factory.createIdentifierParser();
        assertNotNull(idp);

        LiteralParser lit = factory.createLiteralParser();
        assertNotNull(lit);

        CallFunctionParser call = factory.createCallFunctionParser();
        assertNotNull(call);

        LineExpressionParser line = factory.createLineExpressionParser();
        assertNotNull(line);
    }
}
