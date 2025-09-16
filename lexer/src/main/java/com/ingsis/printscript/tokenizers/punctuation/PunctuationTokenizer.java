/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.punctuation;

import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokenizers.punctuation.braces.LeftBraceTokenizer;
import com.ingsis.printscript.tokenizers.punctuation.braces.RightBraceTokenizer;
import com.ingsis.printscript.tokenizers.punctuation.parenthesis.LeftParenthesisTokenizer;
import com.ingsis.printscript.tokens.TokenInterface;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

public class PunctuationTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final Collection<Class<? extends PunctuationTokenizer>> subclasses;

    public PunctuationTokenizer() {
        this(new FinalTokenizer());
    }

    public PunctuationTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
        subclasses = List.of(
                CommaTokenizer.class,
                LeftBraceTokenizer.class,
                RightBraceTokenizer.class,
                LeftParenthesisTokenizer.class,
                RightBraceTokenizer.class
        );
    }

    @Override
    public Boolean canTokenize(String input) {
        for (Class<? extends PunctuationTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface tokenizer = subclass.getDeclaredConstructor().newInstance();
                if (tokenizer.canTokenize(input)) {
                    return true;
                }
            } catch (NoSuchMethodException
                    | InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException ignored) {
            }
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
            } catch (NoSuchMethodException
                    | InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException ignored) {
            }
        }
        return nextTokenizer.tokenize(input);
    }
}
