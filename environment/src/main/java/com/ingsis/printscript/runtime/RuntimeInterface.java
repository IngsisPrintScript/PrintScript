package com.ingsis.printscript.runtime;


import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.environment.EnvironmentInterface;

public interface RuntimeInterface {
    EnvironmentInterface currentEnv();
    Result<EnvironmentInterface> pushEnv(EnvironmentInterface env);
    Result<EnvironmentInterface> popEnv();
}
