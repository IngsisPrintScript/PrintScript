/*
 * My Project
 */

package com.ingsis.nodes;

import com.ingsis.visitors.Visitable;

public interface Node extends Visitable {
    Integer line();

    Integer column();
}
