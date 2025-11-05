/*
 * My Project
 */

package com.ingsis.syntactic.parsers;

import com.ingsis.nodes.Node;

public interface ParserRegistry<T extends Node> extends Parser<T> {
  void registerParser(Parser<? extends T> parser);
}
