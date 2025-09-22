/*
 * My Project
 */

package com.ingsis.nodes.operator;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;

public interface OperatorNode extends Node {
    Result<Object> execute();
}
