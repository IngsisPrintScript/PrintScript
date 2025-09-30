/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.tokenstream.TokenStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface TokenStreamFactory {
    TokenStream createCliTokenStream(Queue<Character> buffer);

    TokenStream createFileTokenStream(Path filePath) throws IOException;
}
