package lexer;

import java.util.List;
import results.Result;
import stream.TokenStreamInterface;

public interface LexicalInterface {
  Result<TokenStreamInterface> analyze(List<String> inputs);
}
