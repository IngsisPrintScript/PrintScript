package formatter;

import common.Node;
import results.Result;

public interface FormatterInterface {
    Result<String> format(Node root);
}
