/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.utils.nodes.nodes.Node;

public interface ParserRegistry<T extends Node> extends Parser<T> {
    void registerParser(Parser<? extends T> parser);
}
