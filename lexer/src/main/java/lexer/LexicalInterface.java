package lexer;

import responses.Result;
import stream.TokenStreamInterface;

import java.util.List;

public interface LexicalInterface {
    Result<TokenStreamInterface> analyze(List<String> inputs);
}
