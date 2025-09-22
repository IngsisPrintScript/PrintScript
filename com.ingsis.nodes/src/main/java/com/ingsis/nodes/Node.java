/*
 * My Project
 */

package com.ingsis.nodes;

import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Visitable;

public interface Node extends Visitable, Checkable, Interpretable {}
