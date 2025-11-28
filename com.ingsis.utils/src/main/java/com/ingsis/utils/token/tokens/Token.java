/*
 * My Project
 */

package com.ingsis.tokens;

public interface Token {
    String name();

    Integer line();

    Integer column();

    String value();
}
