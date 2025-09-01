package parser;

import responses.Result;

import java.util.List;

public interface CodeParserInterface {
    Result<List<String>> parse(String code);
}
