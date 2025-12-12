/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.categories;

public enum TokenCategory {
    SEPARATOR(0, "SEPARATOR_TOKEN"),
    OPERATOR(1, "OPERATOR_TOKEN"),
    KEYWORD(2, "KEYWORD_TOKEN"),
    TYPE(3, "TYPE_TOKEN"),
    BOOLEAN_LITERAL(4, "LITERAL_TOKEN"),
    NUMBER_LITERAL(5, "LITERAL_TOKEN"),
    STRING_LITERAL(6, "LITERAL_TOKEN"),
    IDENTIFIER(7, "IDENTIFIER_TOKEN"),
    TRIVIA(1000, "TRIVIA_TOKEN");

    private final int priority;
    private final String familyName;

    TokenCategory(int priority, String familyName) {
        this.priority = priority;
        this.familyName = familyName;
    }

    public int priority() {
        return this.priority;
    }

    public String familyName() {
        return this.familyName;
    }
}
