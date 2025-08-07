package analyzers.lexic;

import analyzers.Analyzer;
import analyzers.lexic.tokenizers.TokenizerInterface;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import token.TokenInterface;

import java.util.LinkedList;
import java.util.List;

public record LexicalAnalyzer(TokenizerInterface tokenizerInterface) implements Analyzer {
    @Override
    public Response analyze(String line) {
        List<String> words = textSeparator(line);
        List<TokenInterface> tokens = new LinkedList<>();
        for(String word : words) {
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
