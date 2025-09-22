/*
 * My Project
 */

package com.ingsis.tokens;

public record DefaultToken(String name, String value) implements Token {

    public DefaultToken(Token token) {
        this(token.name(), token.value());
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
