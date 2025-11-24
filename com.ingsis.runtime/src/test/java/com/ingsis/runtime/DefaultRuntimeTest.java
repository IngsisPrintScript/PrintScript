package com.ingsis.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.DefaultFunctionEntry;
import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.types.Types;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultRuntimeTest {

    private final DefaultRuntime runtime = DefaultRuntime.getInstance();

    @BeforeEach
    void push() {
        runtime.push();
    }

    @AfterEach
    void pop() {
        runtime.pop();
    }

    @Test
    void singletonInstanceIsSame() {
        DefaultRuntime a = DefaultRuntime.getInstance();
        DefaultRuntime b = DefaultRuntime.getInstance();
        assertSame(a, b);
    }

    @Test
    void pushAndPopAndPushClosureAndExecutionError() {
        Result<Environment> pushed = runtime.push();
        assertNotNull(pushed);

        // create a dummy function entry to provide a closure environment
        DefaultEntryFactory entryFactory = new DefaultEntryFactory();
        DefaultFunctionEntry fn =
                (DefaultFunctionEntry)
                        entryFactory.createFunctionEntry(Types.NIL, Map.of(), null, runtime.getCurrentEnvironment());
        Result<Environment> pushedClosure = runtime.pushClosure(fn);
        assertNotNull(pushedClosure);

        // pop back one level
        Result<Environment> popped = runtime.pop();
        assertNotNull(popped);

        // set/get execution error
        IncorrectResult<String> err = new IncorrectResult<>("boom");
        runtime.setExecutionError(err);
        assertEquals(err, runtime.getExecutionError());
    }
}
