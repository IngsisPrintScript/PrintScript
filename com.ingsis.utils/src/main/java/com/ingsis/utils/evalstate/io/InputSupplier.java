/*
 * My Project
 */

package com.ingsis.utils.evalstate.io;

import com.ingsis.utils.value.Value;

@FunctionalInterface
public interface InputSupplier {
    Value supply();
}
