/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

public interface ParserRegistry extends Parser {
    void registerTokenizer(Parser parser);
}
