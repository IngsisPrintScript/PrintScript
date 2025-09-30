/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.syntactic.SyntacticParser;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface SyntacticFactory {
    SyntacticParser createCliSyntacticChecker(Queue<Character> buffer);

    SyntacticParser createFileSyntacticChecker(Path filePath) throws IOException;
}
