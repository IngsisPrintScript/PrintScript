/*
 * My Project
 */

package com.ingsis.utils.evalstate.io;

@FunctionalInterface
public interface OutputEmitter {
    void emit(String value);
}
