/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.result.Result;

public interface Checkable {
    Result<String> acceptChecker(Checker checker);
}
