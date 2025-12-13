/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.factory;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import java.util.function.Supplier;

public interface ParserFactory {
    Parser<Node> conditionalParser(
            Parser<ExpressionNode> conditionParser, Supplier<Parser<Node>> programParser);

    Parser<Node> declarationParser(
            Parser<ExpressionNode> identifierParser, Parser<ExpressionNode> expressionParser);

    Parser<Node> lineExpressionParser(Parser<ExpressionNode> expressionParser);

    Parser<ExpressionNode> operatorParser(Supplier<Parser<ExpressionNode>> leafParserSupplier);

    Parser<ExpressionNode> callFunctionParser(
            Parser<ExpressionNode> identifierParser,
            Supplier<Parser<ExpressionNode>> leafParserSupplier);

    Parser<ExpressionNode> numberLiteralParser();

    Parser<ExpressionNode> stringLiteralParser();

    Parser<ExpressionNode> booleanLiteralParser();

    Parser<ExpressionNode> identifierParser();
}
