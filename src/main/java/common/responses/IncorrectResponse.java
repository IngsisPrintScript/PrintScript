package common.responses;

public record IncorrectResponse(String message) implements Response {
    @Override
    public Boolean isSuccessful() {
        return false;
    }
}
