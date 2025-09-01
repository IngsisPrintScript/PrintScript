package iterator;

public class MockCodeIterator implements CodeIteratorInterface {
    private final String code;
    private Boolean hasGottenCode;

    public MockCodeIterator(String code) {
        this.code = code;
        hasGottenCode = false;
    }

    @Override
    public boolean hasNext() {
        return !hasGottenCode;
    }

    @Override
    public String next() {
        hasGottenCode = true;
        return code;
    }
}
