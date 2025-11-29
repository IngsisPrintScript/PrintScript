/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes; /*
                                       * My Project
                                       */

import com.ingsis.utils.nodes.visitors.Visitable;

public interface Node extends Visitable {
    Integer line();

    Integer column();
}
