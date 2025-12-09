/*
 * My Project
 */

package com.ingsis.utils.nodes;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.token.tokenstream.TokenStream;

public interface Node extends Checkable {
    TokenStream stream();

    Integer line();

    Integer column();
}
