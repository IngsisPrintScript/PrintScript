/*
 * My Project
 */

package com.ingsis.printscript.visitor;

import com.ingsis.printscript.results.Result;

public interface SemanticallyCheckable {
    Result<String> acceptCheck(RuleVisitor checker);
}
