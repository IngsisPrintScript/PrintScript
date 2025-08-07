package analyzers;

import responses.Response;

import java.util.List;

public interface Analyzer {
    Response analyze(List<String> words);
}
