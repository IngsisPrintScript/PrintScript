package analyzers.lexic;

import analyzers.lexic.tokenizers.TOKENIZE_INTERFACE.Tokenizer;
import responses.IncorrectResponse;
import responses.Response;
import token.TokenInterfaces.Token;

import java.util.LinkedList;
import java.util.List;

public class Lexical{
    private List<Tokenizer> tokenizer;

    public Lexical(List<Tokenizer> tokenizer) {
        this.tokenizer = tokenizer;
    }

    public LinkedList<Response<Token>> parse(String line){
        LinkedList<Response<Token>> tokens = new LinkedList<>();
        String[] token = line.split(" ");
        for(String word : token){
            Response<Token> tokenResponse = searchTokens(word);
            tokens.add(tokenResponse);

        }
        return tokens;
    }

    private Response<Token> searchTokens(String word){
        Response<Token> tokenToGet = new IncorrectResponse("No Coincidence Tokens");
        for(Tokenizer tokenizer : tokenizer){
            if(tokenizer.canTokenize(word)){
                return tokenizer.tokenize(word);
            }
        }
        return tokenToGet;
    }
}
