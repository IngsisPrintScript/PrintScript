package lexer.tokenizers.punctuation;

import responses.Result;
import lexer.tokenizers.FinalTokenizer;
import lexer.tokenizers.TokenizerInterface;
import lexer.tokenizers.punctuation.parenthesis.LeftParenthesisTokenizer;
import lexer.tokenizers.punctuation.parenthesis.RightParenthesisTokenizer;

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
    public Result tokenize(String input) {
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
