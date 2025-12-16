/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.visitors.CheckResult;

public interface NodeEventHandler<T extends Node> {
    CheckResult handle(T node, SemanticEnvironment env);
}
