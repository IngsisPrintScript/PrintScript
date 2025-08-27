package lexer.tokenizers.operator;

import responses.Result;
import lexer.tokenizers.TokenizerInterface;

import java.util.List;

public class OperatorTokenizer implements TokenizerInterface {
    private final TokenizerInterface nextTokenizer;
    private final List<Class<? extends OperatorTokenizer>> subclasses = List.of(AssignationOperatorTokenizer.class);

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
    public Result tokenize(String input) {
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
