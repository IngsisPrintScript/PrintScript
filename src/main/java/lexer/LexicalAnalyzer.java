package lexer;

import common.interfaces.Analyzer;
import common.responses.CorrectResponse;
import common.responses.IncorrectResponse;
import common.responses.Response;
import common.token.TokenInterface;
import lexer.tokenizers.TokenizerInterface;
import lexer.repositories.code.CodeRepositoryInterface;

import java.util.LinkedList;
import java.util.List;

public record LexicalAnalyzer(TokenizerInterface tokenizerInterface, CodeRepositoryInterface repository) implements Analyzer {
    @Override
    public Response analyze() {
        Response getCodeResponse = repository().getCode();
        if (!getCodeResponse.isSuccessful()){
            return getCodeResponse;
        }

        if (!(( (CorrectResponse<?>) getCodeResponse).newObject() instanceof List<?> elements)){
            return new IncorrectResponse("Code repository did not returned a list of words.");
        }
        List<TokenInterface> tokens = new LinkedList<>();
        for(Object object : elements) {
            if (!(object instanceof String word)){
                return new IncorrectResponse("Object is not a string.");
            }
            Response tokenizerResponse = tokenizerInterface.tokenize(word);
            if(!tokenizerResponse.isSuccessful()) {
                return tokenizerResponse;
            }
            Object token = ((CorrectResponse<?>) tokenizerResponse).newObject();
            if(token instanceof TokenInterface) {
                tokens.add((TokenInterface) token);
            } else {
                return new IncorrectResponse("Tokenizer returned a correct response with no token");
            }
        }
        return new CorrectResponse<List<TokenInterface>>(tokens);
    }

    private List<String> textSeparator(String line){
        return List.of(line.split(" "));
    }
}
