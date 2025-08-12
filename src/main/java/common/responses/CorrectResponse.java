package common.responses;

public record CorrectResponse<T>(T newObject) implements Response {
    @Override
    public Boolean isSuccessful() {
        return true;
    }
}
