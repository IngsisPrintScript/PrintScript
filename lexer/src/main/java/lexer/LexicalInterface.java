package lexer;

import responses.Result;

import java.util.List;

public interface LexicalInterface {
    Result analyze(List<String> inputs);
}
