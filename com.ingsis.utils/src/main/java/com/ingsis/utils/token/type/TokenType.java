/*
 * My Project
 */

package com.ingsis.utils.token.type;

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.Set;

public enum TokenType {
    // ===== Identifiers & Literals =====
    IDENTIFIER(null, "[a-zA-Z_][a-zA-Z0-9_]*", null),
    NUMBER_LITERAL(null, Types.NUMBER.regEx(), "\\d+\\.?"),
    STRING_LITERAL(null, Types.STRING.regEx(), "\"(?:\\\\.|[^\\\\\"]*)?"),
    BOOLEAN_LITERAL(null, Types.BOOLEAN.regEx(), "t|f|tr|fa|tru|fals"),

    // ===== Keywords =====
    LET("let", null, null),
    CONST("const", null, null),
    IF("if", null, null),
    ELSE("else", null, null),

    // ===== Types =====
    NUMBER("number", null, null),
    STRING("string", null, null),
    BOOLEAN("boolean", null, null),

    // ===== Operators =====
    PLUS("+", null, null),
    MINUS("-", null, null),
    STAR("*", null, null),
    SLASH("/", null, null),
    EQUAL("=", null, null),

    // ===== Separators =====
    LPAREN("(", null, null),
    RPAREN(")", null, null),
    LBRACE("{", null, null),
    RBRACE("}", null, null),
    COLON(":", null, null),
    SEMICOLON(";", null, null),
    COMMA(",", null, null),

    // ===== Space Separators =====
    SPACE(" ", null, null),
    NEWLINE("\n", null, null),
    TAB("\t", null, null),
    CRETURN("\r", null, null);

    private final String fixedLexeme;
    private final String regexPattern;
    private final String prefixPattern;

    public static final Set<TokenType> KEYWORDS = Set.of(LET, CONST, IF, ELSE);
    public static final Set<TokenType> TYPES = Set.of(NUMBER, STRING, BOOLEAN);
    public static final Set<TokenType> OPERATORS = Set.of(PLUS, MINUS, STAR, SLASH, EQUAL);
    public static final Set<TokenType> SEPARATORS =
            Set.of(LPAREN, RPAREN, LBRACE, RBRACE, COLON, SEMICOLON, COMMA);
    public static final Set<TokenType> TRIVIA = Set.of(SPACE, NEWLINE, TAB, CRETURN);

    TokenType(String fixedLexeme, String regexPattern, String prefixPattern) {
        this.fixedLexeme = fixedLexeme;
        this.regexPattern = regexPattern;
        this.prefixPattern = prefixPattern;
    }

    public String lexeme() {
        return fixedLexeme;
    }

    public String pattern() {
        return regexPattern;
    }

    public String prefixPattern() {
        return prefixPattern;
    }

    public boolean matches(String input) {
        if (regexPattern == null) return false;
        return input != null && input.matches(regexPattern);
    }

    public boolean matchesPrefix(String input) {
        if (prefixPattern == null) return false;
        return input != null && input.matches(prefixPattern);
    }

    public static Result<TokenType> fromString(String input) {

        TokenType bestMatch = null;
        int bestScore = -1;

        for (TokenType t : values()) {

            // Highest priority: exact lexeme
            if (t.lexeme() != null && t.lexeme().equals(input)) {
                int score = 1000; // unbeatable
                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = t;
                }
                continue;
            }

            // Full regex match
            if (t.regexPattern != null && input.matches(t.regexPattern)) {
                int score = 500 + t.regexPattern.length(); // prefer more specific regex
                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = t;
                }
                continue;
            }

            // Prefix regex match
            if (t.prefixPattern != null && input.matches(t.prefixPattern)) {
                int score = 100 + t.prefixPattern.length();
                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = t;
                }
            }
        }

        if (bestMatch != null) {
            return new CorrectResult<>(bestMatch);
        }

        return new IncorrectResult<>("No token type for string: " + input);
    }

    public static boolean isKeyword(String input) {
        return input != null && KEYWORDS.stream().anyMatch(k -> k.fixedLexeme.equals(input));
    }

    public static boolean isType(String type) {
        return type != null && TYPES.stream().anyMatch(k -> k.fixedLexeme.equals(type));
    }

    public static boolean isOperator(String input) {
        return input != null && OPERATORS.stream().anyMatch(o -> o.fixedLexeme.equals(input));
    }

    public static boolean isSeparator(String input) {
        return input != null && SEPARATORS.stream().anyMatch(s -> s.fixedLexeme.equals(input));
    }
}
