/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors; /*
                                          * My Project
                                          */

import com.ingsis.utils.result.Result;

public interface Visitable {
    Result<String> acceptVisitor(Visitor visitor);
}
