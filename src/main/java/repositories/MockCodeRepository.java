package repositories;

public record MockCodeRepository(String mockCode) implements CodeRepositoryInterface {
    @Override
    public String getCode() {
        return mockCode();
    }
}
