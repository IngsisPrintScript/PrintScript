/*
 * My Project
 */

package com.ingsis.utils.type.types; /*
                                      * My Project
                                      */

import java.util.Arrays;
import java.util.List;

public enum Types {
    NUMBER("number", "-?\\d+(\\.\\d+)?"),
    BOOLEAN("boolean", "\\b(?:true|false)\\b"),
    NIL("nil", "\\bnil\\b"),
    UNDEFINED("undefined", "\\bundefined\\b"),
    STRING("string", "([^\"\\\\]|\\\\.)*");

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

    public boolean isCompatibleWith(Object object) {
        String string = object.toString();
        return this.checkFormat(string);
    }

    public boolean isCompatibleWith(Types other) {
        if (this == UNDEFINED || other == UNDEFINED) return true;
        return this.equals(other);
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
