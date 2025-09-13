/*
 * My Project
 */

package com.ingsis.printscript.astnodes.visitor;

import com.ingsis.printscript.results.Result;

public interface SemanticallyCheckable {
    Result<String> acceptCheck(RuleVisitor checker);
}
