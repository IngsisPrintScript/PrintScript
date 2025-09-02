package lexer;


import common.TokenInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;
import stream.TokenStream;
import stream.TokenStreamInterface;

import java.util.ArrayList;
import java.util.List;

public record Lexical(TokenizerInterface tokenizer) implements LexicalInterface {
    @Override
    public Result<TokenStreamInterface> analyze(List<String> inputs) {
        List<TokenInterface> tokens = new ArrayList<>();
        for (String input : inputs) {
            Result<TokenInterface> result = tokenizer.tokenize(input);
            if (!result.isSuccessful()) {
                return new IncorrectResult<>(result.errorMessage());
            }
            TokenInterface token = result.result();
            tokens.add(token);
        }
        return new CorrectResult<>(new TokenStream(tokens));
    }
}
