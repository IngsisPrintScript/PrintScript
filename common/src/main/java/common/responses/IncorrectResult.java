package common.responses;

public record IncorrectResult(String errorMessage) implements Result {
    @Override
    public Boolean isSuccessful() {
        return false;
    }
}
