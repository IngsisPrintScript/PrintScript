/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.result.Result;

public interface Checkable {
    Result<String> acceptChecker(Checker checker);
}
