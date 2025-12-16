/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.factory;

import com.ingsis.parser.syntactic.parsers.CallFunctionParser;
import com.ingsis.parser.syntactic.parsers.ConditionalParser;
import com.ingsis.parser.syntactic.parsers.DeclarationParser;
import com.ingsis.parser.syntactic.parsers.LineExpressionParser;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.atomic.IdentifierParser;
import com.ingsis.parser.syntactic.parsers.atomic.literals.BooleanLiteralParser;
import com.ingsis.parser.syntactic.parsers.atomic.literals.NumberLiteralParser;
import com.ingsis.parser.syntactic.parsers.atomic.literals.StringLiteralParser;
import com.ingsis.parser.syntactic.parsers.operator.PrattParser;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;
import java.util.function.Supplier;

public class InMemoryParserFactory implements ParserFactory {
    private final TokenTemplateFactory tokenTemplateFactory;
    private final NodeFactory nodeFactory;

    public InMemoryParserFactory(
            TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
        this.tokenTemplateFactory = tokenTemplateFactory;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public Parser<Node> conditionalParser(
            Parser<ExpressionNode> conditionParser, Supplier<Parser<Node>> programParser) {
        return new ConditionalParser(
                tokenTemplateFactory.keyword(TokenType.IF.lexeme()).result(),
                tokenTemplateFactory.keyword(TokenType.ELSE.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.LPAREN.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.RPAREN.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.LBRACE.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.RBRACE.lexeme()).result(),
                conditionParser,
                programParser,
                this.nodeFactory);
    }

    @Override
    public Parser<Node> declarationParser(
            Parser<ExpressionNode> identifierParser, Parser<ExpressionNode> expressionParser) {
        return new DeclarationParser(
                List.of(
                        tokenTemplateFactory.keyword(TokenType.LET.lexeme()).result(),
                        tokenTemplateFactory.keyword(TokenType.CONST.lexeme()).result()),
                tokenTemplateFactory.separator(TokenType.COLON.lexeme()).result(),
                List.of(
                        tokenTemplateFactory.type(TokenType.STRING.lexeme()).result(),
                        tokenTemplateFactory.type(TokenType.NUMBER.lexeme()).result(),
                        tokenTemplateFactory.type(TokenType.BOOLEAN.lexeme()).result()),
                tokenTemplateFactory.operator(TokenType.EQUAL.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.SEMICOLON.lexeme()).result(),
                identifierParser,
                expressionParser,
                this.nodeFactory);
    }

    @Override
    public Parser<ExpressionNode> operatorParser(
            Supplier<Parser<ExpressionNode>> leafParserSupplier) {
        return new PrattParser(leafParserSupplier, this.nodeFactory);
    }

    @Override
    public Parser<ExpressionNode> callFunctionParser(
            Parser<ExpressionNode> identifierParser,
            Supplier<Parser<ExpressionNode>> leafParserSupplier) {
        return new CallFunctionParser(
                tokenTemplateFactory.separator(TokenType.LPAREN.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.COMMA.lexeme()).result(),
                tokenTemplateFactory.separator(TokenType.RPAREN.lexeme()).result(),
                identifierParser,
                leafParserSupplier,
                this.nodeFactory);
    }

    @Override
    public Parser<ExpressionNode> numberLiteralParser() {
        return new NumberLiteralParser(this.tokenTemplateFactory, this.nodeFactory);
    }

    @Override
    public Parser<ExpressionNode> stringLiteralParser() {
        return new StringLiteralParser(this.tokenTemplateFactory, this.nodeFactory);
    }

    @Override
    public Parser<ExpressionNode> booleanLiteralParser() {
        return new BooleanLiteralParser(this.tokenTemplateFactory, this.nodeFactory);
    }

    @Override
    public Parser<ExpressionNode> identifierParser() {
        return new IdentifierParser(this.tokenTemplateFactory, this.nodeFactory);
    }

    @Override
    public Parser<Node> lineExpressionParser(Parser<ExpressionNode> expressionParser) {
        return new LineExpressionParser(
                tokenTemplateFactory.separator(TokenType.SEMICOLON.lexeme()).result(),
                expressionParser);
    }
}
