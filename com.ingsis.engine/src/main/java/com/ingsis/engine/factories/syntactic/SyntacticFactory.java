/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.syntactic.SyntacticParser;
import java.io.IOException;
import java.nio.file.Path;

public interface SyntacticFactory {

    SyntacticParser createCliSyntacticChecker(String input) throws IOException;

    SyntacticParser createFileSyntacticChecker(Path filePath) throws IOException;

    SyntacticParser createReplSyntacticChecker() throws IOException;
}
