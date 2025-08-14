package lexical.tokenizer.type;

import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

import java.util.List;

public class TypeTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final List<Class<? extends TypeTokenizer>> subclasses = List.of(StringTypeTokenizer.class, NumberTypeTokenizer.class);

    public TypeTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
    }

    @Override
    public Boolean canTokenize(String token) {
        for (Class<? extends TypeTokenizer> subclass : subclasses) {
            try{
                TokenizerInterface subclassTokenizer = subclass.getDeclaredConstructor().newInstance();
                if (subclassTokenizer.canTokenize(token)) {
                    return true;
                }
            } catch (Exception e){
                continue;
            }
        }
        return false;
    }

    @Override
    public Result tokenize(String token) {
        for (Class<? extends TypeTokenizer> subclass : subclasses) {
            try{
                TokenizerInterface subclassTokenizer = subclass.getDeclaredConstructor().newInstance();
                if (subclassTokenizer.canTokenize(token)) {
                    return subclassTokenizer.tokenize(token);
                }
            } catch (Exception e){
                continue;
            }
        }
        return nextTokenizer.tokenize(token);
    }
}
