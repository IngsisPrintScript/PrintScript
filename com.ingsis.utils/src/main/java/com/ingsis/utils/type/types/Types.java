/*
 * My Project
 */

package com.ingsis.utils.type.types; /*
                                      * My Project
                                      */

import java.util.Arrays;
import java.util.List;

public enum Types {
  NUMBER("number", "(\\d+\\.\\d*|\\.\\d+|\\d+)", Number.class),
  BOOLEAN("boolean", "\\b(?:true|false)\\b", Boolean.class),
  NIL("nil", "\\bnil\\b", null),
  UNDEFINED("undefined", "\\bundefined\\b", null),
  STRING("string", "([^\"\\\\]|\\\\.)*", String.class);

  private final String keyword;
  private final String regEx;
  private final Class<?> associatedJavaClass;

  Types(String keyword, String regex, Class<?> clazz) {
    this.keyword = keyword;
    this.regEx = regex;
    this.associatedJavaClass = clazz;
  }

  public String keyword() {
    return keyword;
  }

  public String regEx() {
    return regEx;
  }

  public Class<?> getAssociatedJavaClass() {
    return associatedJavaClass;
  }

  public boolean isCompatibleWith(Object object) {
    if (this == UNDEFINED || this == NIL)
      return true;
    return associatedJavaClass != null && associatedJavaClass.isInstance(object);
  }

  public boolean isCompatibleWith(Types other) {
    if (this == UNDEFINED || other == UNDEFINED)
      return true;
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
