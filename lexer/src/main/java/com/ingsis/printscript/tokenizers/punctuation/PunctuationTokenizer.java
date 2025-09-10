package com.ingsis.printscript.tokenizers.punctuation;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokenizers.punctuation.parenthesis.LeftParenthesisTokenizer;
import com.ingsis.printscript.tokenizers.punctuation.parenthesis.RightParenthesisTokenizer;

import java.util.List;

public class PunctuationTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final List<Class<? extends PunctuationTokenizer>> subclasses = List.of(LeftParenthesisTokenizer.class, RightParenthesisTokenizer.class);

    public PunctuationTokenizer() {
        this(new FinalTokenizer());
    }
    public PunctuationTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
    }

    @Override
    public Boolean canTokenize(String input) {
        for (Class<? extends PunctuationTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface tokenizer = subclass.getDeclaredConstructor().newInstance();
                if (tokenizer.canTokenize(input)) {
                    return true;
                }
            } catch (Exception ignored){}
        }
        return false;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        for (Class<? extends PunctuationTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface tokenizer = subclass.getDeclaredConstructor().newInstance();
                if (tokenizer.canTokenize(input)) {
                    return tokenizer.tokenize(input);
                }
            } catch (Exception ignored){}
        }
        return nextTokenizer.tokenize(input);
    }
}
