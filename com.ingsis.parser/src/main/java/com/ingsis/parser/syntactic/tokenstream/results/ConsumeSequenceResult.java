/*
 * My Project
 */

package com.ingsis.parser.syntactic.tokenstream.results;

import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.utils.nodes.Node;
import java.util.List;

public sealed interface ConsumeSequenceResult<T extends Node> {
    public record CORRECT<T extends Node>(List<T> parsedNodes, TokenStream stream)
            implements ConsumeSequenceResult<T> {}

    public record INCORRECT<T extends Node>(String error) implements ConsumeSequenceResult<T> {}
}
