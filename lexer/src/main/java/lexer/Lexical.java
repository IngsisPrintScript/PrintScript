package lexer;


import common.TokenInterface;
import responses.CorrectResult;
import responses.Result;
import lexer.tokenizers.TokenizerInterface;

import java.util.ArrayList;
import java.util.List;

public record Lexical(TokenizerInterface tokenizer) implements LexicalInterface {
    @Override
    public Result analyze(List<String> inputs) {
        List<TokenInterface> tokens = new ArrayList<>();
        for (String input : inputs) {
            Result result = tokenizer.tokenize(input);
            if (!result.isSuccessful()) {
                return result;
            }
            TokenInterface token = ( (CorrectResult<TokenInterface>) result).newObject();
            tokens.add(token);
        }
        return new CorrectResult<>(tokens);
    }
}
