package formatter;

import common.Node;
import formatter.FormatterRules.FactoryFormatterRules;
import formatter.FormatterRules.FormatterRules;
import results.Result;

import java.util.HashMap;

public record Formatter(ReadRules readRules) {
    public Result<String> format(Node root) {
        HashMap<FormatterRules, Boolean> rulesToFormat = readRules.readRules();
        return root.accept(new FormatterVisitor(new FactoryFormatterRules(rulesToFormat)));
    }
}
