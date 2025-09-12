package com.ingsis.printscript.tokenizers.operator;

import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokenizers.TokenizerInterface;

import java.util.List;

public class OperatorTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final List<Class<? extends OperatorTokenizer>> subclasses = List.of(AssignationOperatorTokenizer.class, AdditionOperatorTokenizer.class);

    public OperatorTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
    }

    @Override
    public Boolean canTokenize(String input) {
        for (Class<? extends OperatorTokenizer> subclass : subclasses) {
            try{
                TokenizerInterface subclassTokenizer = subclass.getDeclaredConstructor().newInstance();
                if (subclassTokenizer.canTokenize(input)) {
                    return true;
                }
            } catch (Exception e){
                continue;
            }
        }
        return false;
    }

    @Override
    public Result<TokenInterface> tokenize(String input) {
        for (Class<? extends OperatorTokenizer> subclass : subclasses) {
            try{
                TokenizerInterface subclassTokenizer = subclass.getDeclaredConstructor().newInstance();
                if (subclassTokenizer.canTokenize(input)) {
                    return subclassTokenizer.tokenize(input);
                }
            } catch (Exception e){
                continue;
            }
        }
        return nextTokenizer.tokenize(input);
    }
}
