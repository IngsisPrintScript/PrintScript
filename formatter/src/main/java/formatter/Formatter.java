package formatter;

import common.Node;
import formatter.FormatterRules.FactoryFormatterRules;
import formatter.FormatterRules.FormatterRules;
import formatter.yamlAnalizer.ReadRules;
import results.Result;

import java.util.HashMap;

public record Formatter(ReadRules readRules) implements FormatterInterface {

    @Override
    public Result<String> format(Node root) {
        HashMap<FormatterRules, Boolean> rulesToFormat = readRules.readRules();
        return root.accept(new FormatterVisitor(new FactoryFormatterRules(rulesToFormat)));
    }
}
