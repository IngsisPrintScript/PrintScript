/*
 * My Project
 */

package com.ingsis.types;

public enum Types {
    STRING("String"),
    NUMBER("Number"),
    BOOLEAN("Boolean"),
    NIL("");

    private final String keyword;

    Types(String keyword) {
        this.keyword = keyword;
    }

    public String keyword() {
        return keyword;
    }

    public static Types fromKeyword(String input) {
        for (Types t : values()) {
            if (t.keyword.equals(input)) {
                return t;
            }
        }
        return null;
    }
}
