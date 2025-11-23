package com.ingsis.engine.versions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VersionTest {
    @Test
    void enumContainsVersions() {
        Version[] vals = Version.values();
        assertTrue(vals.length >= 2);
        assertNotNull(Version.V1_0);
        assertNotNull(Version.V1_1);
    }
}
