/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

public interface ASTreeBuilderInterface {
    Boolean canBuild(TokenStreamInterface tokenStream);

    Result<? extends Node> build(TokenStreamInterface tokenStream);
}
