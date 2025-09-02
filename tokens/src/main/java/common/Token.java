package common;

public record Token(String name, String value) implements TokenInterface {
  @Override
  public boolean equals(Object object) {
    if (object == null) return false;
    if (!(object instanceof Token token)) return false;
    return token.name().equals(this.name());
  }
}
