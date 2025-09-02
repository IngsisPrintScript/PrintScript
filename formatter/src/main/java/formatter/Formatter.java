package formatter;

import common.Node;
import formatter.FormatterRules.SeparationFormatter;
import formatter.yamlAnalizer.ReadRules;
import results.Result;

import java.util.HashMap;

public record Formatter(ReadRules readRules) implements FormatterInterface {

    @Override
    public Result<String> format(Node root) {
        HashMap<String, Object> rulesToFormat = readRules.readRules();
        return root.accept(new FormatterVisitor(new SeparationFormatter(rulesToFormat)));
    }
}
