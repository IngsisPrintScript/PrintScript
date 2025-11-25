/*
 * My Project
 */

package com.ingsis.engine;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.engine.versions.Version;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class CliEngineReflectionTest {

    @Test
    void createSemanticFactoryForBothVersions() throws Exception {
        CliEngine engine = new CliEngine();

        Field versionField = CliEngine.class.getDeclaredField("version");
        versionField.setAccessible(true);

        Method createSemanticFactory = CliEngine.class.getDeclaredMethod("createSemanticFactory");
        createSemanticFactory.setAccessible(true);

        versionField.set(engine, Version.V1_0);
        Object sf1 = createSemanticFactory.invoke(engine);
        assertNotNull(sf1);

        versionField.set(engine, Version.V1_1);
        Object sf2 = createSemanticFactory.invoke(engine);
        assertNotNull(sf2);
    }
}
