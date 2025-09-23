/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

public interface ParserRegistry extends Parser {
    void registerParser(Parser parser);
}
