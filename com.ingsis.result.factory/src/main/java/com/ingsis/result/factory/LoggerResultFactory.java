/*
 * My Project
 */

package com.ingsis.result.factory;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.runtime.Runtime;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public class LoggerResultFactory implements ResultFactory {
    private final ResultFactory SUB_FACTORY;
    private final Runtime RUNTIME;

    public LoggerResultFactory(ResultFactory SUB_FACTORY, Runtime RUNTIME) {
        this.SUB_FACTORY = SUB_FACTORY;
        this.RUNTIME = RUNTIME;
    }

    @Override
    public <T> IncorrectResult<T> createIncorrectResult(String errorMessage) {
        IncorrectResult<T> result = SUB_FACTORY.createIncorrectResult(errorMessage);
        RUNTIME.setExecutionError(result);
        return result;
    }

    @Override
    public <T> IncorrectResult<T> cloneIncorrectResult(IncorrectResult<?> originalIncorrectResult) {
        return SUB_FACTORY.cloneIncorrectResult(originalIncorrectResult);
    }

    @Override
    public <T> CorrectResult<T> createCorrectResult(T result) {
        return SUB_FACTORY.createCorrectResult(result);
    }
}
