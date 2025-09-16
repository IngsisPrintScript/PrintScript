/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.separator;

import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import java.util.Collection;
import java.util.List;

public class SeparatorTokenizer implements TokenizerInterface {
    private final TokenizerInterface NEXT_TOKENIZER;
    private final Collection<Class<? extends SeparatorTokenizer>> subclasses;

    public SeparatorTokenizer(TokenizerInterface NEXT_TOKENIZER) {
        this.NEXT_TOKENIZER = NEXT_TOKENIZER;
        subclasses = List.of(
                NewLineSeparatorTokenizer.class,
                SpaceTokenizer.class,
                TabSeparatorTokenizer.class
        );
    }

    public SeparatorTokenizer() {
        this(new FinalTokenizer());
    }

    @Override
    public Boolean canTokenize(String input) {
        for (Class<? extends SeparatorTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface tokenizer = subclass.getDeclaredConstructor().newInstance();
                if (tokenizer.canTokenize(input)) {
                    return true;
                }
            } catch (RuntimeException rte) {
                throw rte;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        for (Class<? extends SeparatorTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface tokenizer = subclass.getDeclaredConstructor().newInstance();
                if (tokenizer.canTokenize(input)) {
                    return tokenizer.tokenize(input);
                }
            } catch (RuntimeException rte) {
                throw rte;
            } catch (Exception ignored) {
            }
        }
        return NEXT_TOKENIZER.tokenize(input);
    }
}
