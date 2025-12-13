/*
 * My Project
 */

package com.ingsis.utils.nodes;

import com.ingsis.utils.nodes.visitors.Checkable;

public interface Node extends Checkable {
    Integer line();

    Integer column();
}
