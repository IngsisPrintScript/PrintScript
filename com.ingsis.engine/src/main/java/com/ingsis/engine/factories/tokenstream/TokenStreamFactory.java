/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.utils.token.tokenstream.TokenStream;
import java.io.InputStream;

public interface TokenStreamFactory {

    TokenStream fromInputStream(InputStream in);
}
