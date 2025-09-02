package stream;

import common.TokenInterface;
import factories.tokens.TokenFactory;
import java.util.List;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public class TokenStream implements TokenStreamInterface {
  private final List<TokenInterface> tokens;
  private Integer index = 0;

  public TokenStream(List<TokenInterface> tokens) {
    this.tokens = List.copyOf(tokens);
  }

  @Override
  public Result<TokenInterface> peek() {
    if (isEndOfStream()) {
      return new IncorrectResult<>("The stream has no more tokens");
    }
    return new CorrectResult<>(this.tokens().get(index));
  }

  @Override
  public Result<TokenInterface> peek(Integer offset) {
    if (isEndOfStream()) {
      return new IncorrectResult<>("The stream has no more tokens.");
    }
    return new CorrectResult<>(this.tokens().get(index + offset));
  }

  @Override
  public Result<TokenInterface> consume() {
    if (isEndOfStream()) {
      return new IncorrectResult<>("The stream has no more tokens.");
    }
    return new CorrectResult<>(this.tokens().get(index++));
  }

  @Override
  public Result<TokenInterface> consume(TokenInterface expectedToken) {
    if (isEndOfStream()) {
      return new IncorrectResult<>("The stream has no more tokens.");
    }
    TokenInterface token = this.tokens().get(index);
    if (!token.equals(expectedToken)) {
      return new IncorrectResult<>("The actual token is not of the expected type.");
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

  private List<TokenInterface> tokens() {
    return tokens;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TokenStream stream)) {
      return false;
    }
    return stream.tokens.equals(this.tokens);
  }
}
