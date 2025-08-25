package lexer;

import common.responses.Result;

import java.util.List;

public interface LexicalInterface {
    Result analyze(List<String> inputs);
}
