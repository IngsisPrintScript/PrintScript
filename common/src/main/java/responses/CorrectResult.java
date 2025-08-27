package responses;

public record CorrectResult<T>(T newObject) implements Result {
    @Override
    public Boolean isSuccessful() {
        return true;
    }
}
