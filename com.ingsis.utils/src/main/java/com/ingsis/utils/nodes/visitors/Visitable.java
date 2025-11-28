/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.result.Result;

public interface Visitable {
    Result<String> acceptVisitor(Visitor visitor);
}
