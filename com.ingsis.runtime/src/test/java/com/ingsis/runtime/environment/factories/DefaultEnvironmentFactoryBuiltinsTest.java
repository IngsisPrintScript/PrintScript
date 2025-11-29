/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.utils.nodes.nodes.expression.function.GlobalFunctionBody;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class DefaultEnvironmentFactoryBuiltinsTest {

    @Test
    void builtinsExistAndLambdaBodiesExecute() {
        DefaultEnvironmentFactory factory =
                new DefaultEnvironmentFactory(new DefaultEntryFactory());
        Environment env = factory.createGlobalEnvironment();
        assertTrue(env.isFunctionDeclared("println"));
        assertTrue(env.isFunctionDeclared("readInput"));
        assertTrue(env.isFunctionDeclared("readEnv"));

        FunctionEntry printlnEntry = env.readFunction("println").result();
        List body = printlnEntry.body();
        assertNotNull(body);
        Object node = body.get(0);
        assertTrue(node instanceof GlobalFunctionBody);
        GlobalFunctionBody gf = (GlobalFunctionBody) node;
        Object rv = gf.lambda().apply(new Object[] {"hello"});
        assertNull(rv);

        // Exercise readInput lambda by providing input on System.in
        InputStream oldIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream("myline\n".getBytes()));
            FunctionEntry readInputEntry = env.readFunction("readInput").result();
            GlobalFunctionBody rfb = (GlobalFunctionBody) readInputEntry.body().get(0);
            Object out = rfb.lambda().apply(new Object[] {"x"});
            assertNotNull(out);
        } finally {
            System.setIn(oldIn);
        }

        // Exercise readEnv lambda branch: null arg returns null
        FunctionEntry readEnvEntry = env.readFunction("readEnv").result();
        GlobalFunctionBody envBody = (GlobalFunctionBody) readEnvEntry.body().get(0);
        Object nullRv = envBody.lambda().apply(new Object[] {null});
        assertNull(nullRv);
        // non-existing env var returns empty string
        Object empty = envBody.lambda().apply(new Object[] {"FOO_BAR_BOGUS_ENV"});
        assertTrue(empty instanceof String);
    }
}
