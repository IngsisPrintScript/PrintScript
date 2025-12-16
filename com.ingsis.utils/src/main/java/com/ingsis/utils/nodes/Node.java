/*
 * My Project
 */

package com.ingsis.utils.nodes;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;

public interface Node extends Checkable, Interpretable {
    Integer line();

    Integer column();
}
