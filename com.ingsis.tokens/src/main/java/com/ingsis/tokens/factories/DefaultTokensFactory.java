package com.ingsis.tokens.factories;

import com.ingsis.tokens.Token;
import com.ingsis.tokens.TokenInterface;

public record DefaultTokensFactory() implements TokenFactory {
    @Override
    public TokenInterface createKeywordToken(String keyword) {
        return new Token("KEYWORD_TOKEN", keyword);
    }
    @Override
    public TokenInterface createLiteralToken(String literal) {
        return new Token("LITERAL_TOKEN", literal);
    }
    @Override
    public TokenInterface createTypeToken(String type) {
        return new Token("TYPE_TOKEN", type);
    }
    @Override
    public TokenInterface createIdentifierToken(String identifier) {
        return new Token("IDENTIFIER_TOKEN", identifier);
    }
    @Override
    public TokenInterface createSeparatorToken(String separator) {
        return new Token("SEPARATOR_TOKEN", separator);
    }
    @Override
    public TokenInterface createPunctuationToken(String punctuationSign) {
        return new Token("PUNCTUATION_TOKEN", punctuationSign);
    }
    @Override
    public TokenInterface createOperatorToken(String operator) {
        return new Token("OPERATOR_TOKEN", operator);
    }
    @Override
    public TokenInterface createEndOfLineToken(String endOfLine) {
        return new Token("END_OF_LINE_TOKEN", endOfLine);
    }
}
