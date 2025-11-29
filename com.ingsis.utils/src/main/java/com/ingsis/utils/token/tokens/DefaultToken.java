/*
 * My Project
 */

package com.ingsis.utils.token.tokens; /*
                                        * My Project
                                        */

public record DefaultToken(String name, String value, Integer line, Integer column)
        implements Token {

    public DefaultToken(Token token) {
        this(token.name(), token.value(), token.line(), token.column());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token token)) {
            return false;
        }
        if (token.value().isEmpty() || this.value().isEmpty()) {
            return name.equals(token.name());
        } else {
            return name.equals(token.name()) && value.equals(token.value());
        }
    }
}
