/*
 * My Project
 */

package com.ingsis.types;

import java.util.Arrays;
import java.util.List;

public enum Types {
    STRING("String", ".*"),
    NUMBER("Number", "\\d+(\\.\\d+)?"),
    BOOLEAN("Boolean", "true|false"),
    NIL("Nil", ""),
    UNDEFINED("Undefined", "");

    private final String keyword;
    private final String regEx;

    Types(String keyword, String regex) {
        this.keyword = keyword;
        this.regEx = regex;
    }

    public String keyword() {
        return keyword;
    }

    public String regEx() {
        return regEx;
    }

    public boolean checkFormat(String input) {
        return input.matches(this.regEx());
    }

    public static Types fromKeyword(String input) {
        for (Types t : values()) {
            if (t.keyword.equals(input)) {
                return t;
            }
        }
        return UNDEFINED;
    }

    public static List<Types> allTypes() {
        return List.copyOf(Arrays.asList(values()));
    }
}
