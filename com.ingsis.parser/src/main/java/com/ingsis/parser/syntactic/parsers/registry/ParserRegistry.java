/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.registry;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.Node;

public interface ParserRegistry<T extends Node> extends Parser<T> {
  ParserRegistry<T> registerParser(Parser<T> parser);
}
