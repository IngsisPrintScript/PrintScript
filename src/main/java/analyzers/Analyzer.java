package analyzers;

import com.sun.net.httpserver.Request;
import responses.Response;

public interface Analyzer {
    <T> Response<T> analyze(Request request);
}
