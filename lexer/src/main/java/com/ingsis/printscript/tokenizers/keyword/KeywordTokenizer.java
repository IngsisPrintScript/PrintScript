/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.keyword;

import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class KeywordTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final Collection<Class<? extends KeywordTokenizer>> subclasses =
            new ClassGraphReflectionsUtils().findSubclassesOf(KeywordTokenizer.class).find();

    public KeywordTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
    }

    @Override
    public Boolean canTokenize(String input) {
        for (Class<? extends KeywordTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface subclassTokenizer =
                        subclass.getDeclaredConstructor().newInstance();
                if (subclassTokenizer.canTokenize(input)) {
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
        for (Class<? extends KeywordTokenizer> subclass : subclasses) {
            try {
                TokenizerInterface subclassTokenizer =
                        subclass.getDeclaredConstructor().newInstance();
                if (subclassTokenizer.canTokenize(input)) {
                    return subclassTokenizer.tokenize(input);
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
