package com.ingsis.tokens.factories;

import com.ingsis.tokens.Token;
import com.ingsis.tokens.TokenInterface;

public record TokensFactory() {
    TokenInterface createKeywordToken(String keyword) {
        return new Token("KEYWORD_TOKEN", keyword);
    }
    TokenInterface createLiteralToken(String literal) {
        return new Token("LITERAL_TOKEN", literal);
    }
    TokenInterface createTypeToken(String type) {
        return new Token("TYPE_TOKEN", type);
    }
    TokenInterface createIdentifierToken(String identifier) {
        return new Token("IDENTIFIER_TOKEN", identifier);
    }
    TokenInterface createSeparatorToken(String separator) {
        return new Token("SEPARATOR_TOKEN", separator);
    }
    TokenInterface createPunctuationToken(String punctuationSign) {
        return new Token("PUNCTUATION_TOKEN", punctuationSign);
    }
    TokenInterface createOperatorToken(String operator) {
        return new Token("OPERATOR_TOKEN", operator);
    }
    TokenInterface createEndOfLineToken(String endOfLine) {
        return new Token("END_OF_LINE_TOKEN", endOfLine);
    }
}
