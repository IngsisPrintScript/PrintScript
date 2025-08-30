package responses;

public record IncorrectResult<T>(String errorMessage) implements Result<T> {
    @Override
    public Boolean isSuccessful() {
        return false;
    }

    @Override
    public T result() {
        throw new UnsupportedOperationException("Incorrect result does not have a result.");
    }
}
