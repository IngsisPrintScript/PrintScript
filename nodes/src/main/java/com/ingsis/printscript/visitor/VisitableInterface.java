/*
 * My Project
 */

package com.ingsis.printscript.visitor;

import com.ingsis.printscript.results.Result;

public interface VisitableInterface {
    Result<String> accept(VisitorInterface visitor);
}
