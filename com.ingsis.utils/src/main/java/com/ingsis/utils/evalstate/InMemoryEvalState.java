/*
 * My Project
 */

package com.ingsis.utils.evalstate;

import com.ingsis.utils.evalstate.env.Environment;

public record InMemoryEvalState(Environment env) implements EvalState {

    @Override
    public EvalState withEnv(Environment newEnv) {
        return new InMemoryEvalState(newEnv);
    }
}
