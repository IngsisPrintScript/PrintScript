/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.utils.token.tokenstream.TokenStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface TokenStreamFactory {

    TokenStream fromInputStream(InputStream in) throws IOException;

    TokenStream fromFile(Path path) throws IOException;

    TokenStream fromString(CharSequence input) throws IOException;
}
