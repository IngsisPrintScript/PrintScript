/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.expressions.operator;

import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokens.TokenInterface;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class OperatorTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final Collection<Class<? extends OperatorTokenizer>> subclasses =
            new ClassGraphReflectionsUtils().findSubclassesOf(OperatorTokenizer.class).find();

    public OperatorTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
    }

    @Override
    public Boolean canTokenize(String input) {
        for (Class<? extends OperatorTokenizer> subclass : subclasses) {
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
        for (Class<? extends OperatorTokenizer> subclass : subclasses) {
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
