/*
 * My Project
 */

package com.ingsis.printscript.runtime;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.environment.EnvironmentInterface;
import com.ingsis.printscript.runtime.environment.GlobalEnvironment;
import java.util.ArrayDeque;
import java.util.Deque;

public class Runtime implements RuntimeInterface {
    private final Deque<EnvironmentInterface> envStack;

    private Runtime() {
        envStack = new ArrayDeque<>();
        envStack.push(new GlobalEnvironment());
    }

    @Override
    public EnvironmentInterface currentEnv() {
        return envStack.peek();
    }

    @Override
    public Result<EnvironmentInterface> pushEnv(EnvironmentInterface env) {
        envStack.push(env);
        return new CorrectResult<>(env);
    }

    @Override
    public Result<EnvironmentInterface> popEnv() {
        return new CorrectResult<>(envStack.pop());
    }

    private static class RuntimeHelper {
        private static final Runtime INSTANCE = new Runtime();
    }

    public static Runtime getInstance() {
        return RuntimeHelper.INSTANCE;
    }
}
