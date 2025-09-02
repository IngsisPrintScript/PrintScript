package results;

public record CorrectResult<T>(T result) implements Result<T> {
  @Override
  public Boolean isSuccessful() {
    return true;
  }
}
