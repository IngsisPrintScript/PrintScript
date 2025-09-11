package com.ingsis.printscript.formatter;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;

public interface FormatterInterface {
    Result<String> format(Node root);
}
