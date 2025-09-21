/*
 * My Project
 */

package com.ingsis.tokens;

public record DefaultToken(String name, String value) implements Token {
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token token)) {
            return false;
        }
        return name.equals(token.name());
    }
}
