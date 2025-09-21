/*
 * My Project
 */

package com.ingsis.tokens;

public record Token(String name, String value) implements TokenInterface {
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token token)) {
            return false;
        }
        return name.equals(token.name());
    }
}
