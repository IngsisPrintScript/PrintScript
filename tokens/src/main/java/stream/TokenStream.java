package stream;

import factories.tokens.TokenFactory;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.TokenInterface;

import java.util.List;

public class TokenStream implements TokenStreamInterface {
    private final List<TokenInterface> tokens;
    private Integer index = 0;

    public TokenStream(List<TokenInterface> tokens) {
        this.tokens = List.copyOf(tokens);
    }

    @Override
    public Result peek() {
        if (isEndOfStream()){
            return new IncorrectResult("The stream has no more tokens");
        }
        return new CorrectResult<>(this.tokens().get(index));
    }

    @Override
    public Result peek(Integer offset) {
        if (isEndOfStream()){
            return new IncorrectResult("The stream has no more tokens.");
        }
        return new CorrectResult<>(this.tokens().get(index + offset));
    }

    @Override
    public Result consume() {
        if (isEndOfStream()){
            return new IncorrectResult("The stream has no more tokens.");
        }
        return new CorrectResult<>(this.tokens().get(index++));
    }

    @Override
    public Result consume(TokenInterface expectedToken) {
        if (isEndOfStream()) {
            return new IncorrectResult("The stream has no more tokens.");
        }
        TokenInterface token = this.tokens().get(index);
        if (!token.equals(expectedToken)) {
            return new IncorrectResult("The actual token is not of the expected type.");
        }
        index++;
        return new CorrectResult<>(token);
    }

    @Override
    public Boolean isEndOfStream() {
        TokenInterface template = new TokenFactory().createEndOfLineToken();
        return index >= tokens.size();
    }

    @Override
    public Boolean contains(TokenInterface token) {
        for (TokenInterface t : this.tokens()) {
            if (t.equals(token)) {
                return true;
            }
        }
        return false;
    }

    private List<TokenInterface> tokens(){
        return tokens;
    }
}
