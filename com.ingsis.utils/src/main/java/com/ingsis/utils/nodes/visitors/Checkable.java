/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;

public interface Checkable {
  CheckResult acceptChecker(Checker checker, SemanticEnvironment env);
}
