package parser;

import java.util.List;
import results.Result;

public interface CodeParserInterface {
  Result<List<String>> parse(String code);
}
