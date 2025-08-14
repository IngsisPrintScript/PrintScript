package lexical.tokenizer.keyword;

import common.responses.Result;
import lexical.tokenizer.TokenizerInterface;

import java.util.List;

public class KeywordTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final List<Class<? extends KeywordTokenizer>> subclasses = List.of(LetKeywordTokenizer.class, PrintlnKeywordTokenizer.class);

    public KeywordTokenizer(TokenizerInterface nextTokenizer) {
        this.nextTokenizer = nextTokenizer;
    }

    @Override
    public Boolean canTokenize(String token) {
        for (Class<? extends KeywordTokenizer> subclass : subclasses) {
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
        for (Class<? extends KeywordTokenizer> subclass : subclasses) {
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
    }}
