package responses;

public record CorrectResponse<T>(T newObject) implements Response<T>{
    @Override
    public Boolean isSuccessful() {
        return true;
    }

    @Override
    public T getResponse() {
        return this.newObject();
    }
}
