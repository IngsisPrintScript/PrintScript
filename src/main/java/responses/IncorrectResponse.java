package responses;

public record IncorrectResponse<T>(String message) implements Response<String> {
    @Override
    public Boolean isSuccessful() {
        return false;
    }

    @Override
    public String getResponse() {
        return this.message();
    }
}
