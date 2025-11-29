/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors; /*
                                          * My Project
                                          */

import com.ingsis.utils.result.Result;

public interface Checkable {
    Result<String> acceptChecker(Checker checker);
}
