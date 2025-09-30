/*
 * My Project
 */

package com.ingsis.syntactic.factories;

import com.ingsis.syntactic.parsers.ParserRegistry;

public interface ParserChainFactory {
    ParserRegistry createDefaultChain();
}
