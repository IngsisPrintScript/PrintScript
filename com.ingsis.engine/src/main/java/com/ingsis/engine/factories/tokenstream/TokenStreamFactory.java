/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.tokenstream.TokenStream;
import java.io.IOException;
import java.nio.file.Path;

public interface TokenStreamFactory {
    TokenStream createCliTokenStream(String input) throws IOException;

    TokenStream createFileTokenStream(Path filePath) throws IOException;

    TokenStream createReplTokenStream() throws IOException;
}
