package iterator;

import java.util.List;

public class MockCodeIterator implements CodeIteratorInterface {
    private final List<String> code;
    private Boolean hasGottenAllCode;
    private Integer index = 0;

    public MockCodeIterator(List<String> code) {
        this.code = code;
        hasGottenAllCode = false;
    }

    @Override
    public boolean hasNext() {
        return !hasGottenAllCode;
    }

    @Override
    public String next() {
        hasGottenAllCode = index + 1 >= code.size();
        return code.get(index++);
    }
}
