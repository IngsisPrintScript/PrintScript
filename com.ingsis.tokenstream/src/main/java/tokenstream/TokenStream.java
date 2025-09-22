/*
 * My Project
 */

package tokenstream;

import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public interface TokenStream {

    Result<Token> consume();

    Result<Token> consume(Token token);

    Result<Integer> consumeAll(Token token);
}
