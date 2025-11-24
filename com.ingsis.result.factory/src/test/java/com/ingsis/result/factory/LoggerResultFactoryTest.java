package com.ingsis.result.factory;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.Runtime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerResultFactoryTest {

    private LoggerResultFactory loggerFactory;
    private DefaultResultFactory subFactory;
    private Runtime runtime;

    @BeforeEach
    void setUp() {
        subFactory = new DefaultResultFactory();
        runtime = DefaultRuntime.getInstance();
        runtime.setExecutionError(null);
        loggerFactory = new LoggerResultFactory(subFactory, runtime);
    }

    @Test
    void createIncorrectLogsToRuntime() {
        IncorrectResult<String> r = loggerFactory.createIncorrectResult("x");
        assertEquals("x", r.error());
        assertEquals("x", runtime.getExecutionError().error());
    }

    @Test
    void cloneIncorrectLogsToRuntime() {
        Result<?> original = new IncorrectResult<>("copied");
        IncorrectResult<String> r = loggerFactory.cloneIncorrectResult(original);
        assertEquals("copied", r.error());
        assertEquals("copied", runtime.getExecutionError().error());
    }

    @Test
    void createCorrectDoesNotLog() {
        runtime.setExecutionError(null);
        CorrectResult<Integer> c = loggerFactory.createCorrectResult(5);
        assertTrue(c.isCorrect());
        assertNull(runtime.getExecutionError());
    }
}
