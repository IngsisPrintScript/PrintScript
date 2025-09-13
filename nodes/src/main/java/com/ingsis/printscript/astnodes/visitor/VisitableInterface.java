/*
 * My Project
 */

package com.ingsis.printscript.astnodes.visitor;

import com.ingsis.printscript.results.Result;

public interface VisitableInterface {
    Result<String> accept(VisitorInterface visitor);
}
