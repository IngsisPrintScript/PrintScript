/*
 * My Project
 */

package com.ingsis.printscript.tokens.stream;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.tokens.TokenInterface;
import java.util.ArrayList;
import java.util.List;

public class TokenStream implements TokenStreamInterface {
    private List<TokenInterface> tokens;
    private Integer index = 0;

    public TokenStream(List<TokenInterface> tokens) {
        this.tokens = List.copyOf(tokens);
    }

    @Override
    public Result<TokenInterface> peek() {
        if (isEndOfStream()) {
            return new IncorrectResult<>("The stream has no more tokens to peek.");
        }
        return new CorrectResult<>(this.tokens().get(index));
    }

    @Override
    public Result<TokenInterface> peek(Integer offset) {
        if (isEndOfStream()) {
            return new IncorrectResult<>("The stream has no more tokens.");
        } else if (index + offset > tokens().size() - 1) {
            return new IncorrectResult<>("The offset points out of bounds.");
        }
        return new CorrectResult<>(this.tokens().get(index + offset));
    }

    @Override
    public Result<TokenInterface> consume() {
        if (isEndOfStream()) {
            return new IncorrectResult<>("The stream has no more tokens to consume.");
        }
        return new CorrectResult<>(this.tokens().get(index++));
    }

    @Override
    public Result<TokenInterface> consume(TokenInterface expectedToken) {
        if (isEndOfStream()) {
            return new IncorrectResult<>("The stream has no more tokens to consume.");
        }
        TokenInterface token = this.tokens().get(index);
        if (!token.equals(expectedToken)) {
            return new IncorrectResult<>(
                    "Tried to consume " + expectedToken.name() + " but got " + token.name());
        }
        index++;
        return new CorrectResult<>(token);
    }

    @Override
    public Result<Integer> consumeAll(TokenInterface expectedToken) {
        List<TokenInterface> tempList = new ArrayList<>();
        for (TokenInterface token : this.tokens()) {
            if (!token.equals(expectedToken)) {
                tempList.add(token);
            }
        }
        this.tokens = tempList;
        return new CorrectResult<>(tempList.size());
    }

    @Override
    public Boolean isEndOfStream() {
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

    public List<TokenInterface> tokens() {
        return new ArrayList<>(tokens);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TokenStream stream)) {
            return false;
        }
        return stream.tokens.equals(this.tokens);
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }
}
