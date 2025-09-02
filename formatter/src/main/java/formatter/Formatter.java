package formatter;

import common.Node;
import formatter.FormatterRules.FactoryFormatterRules;
import formatter.FormatterRules.FormatterRules;

import java.util.HashMap;

public class Formatter {
    public String format(Node root) {
        HashMap<FormatterRules, Boolean> rulesToFormat = new ReadRules().readRules();
        return root.accept(new FormatterVisitor(new FactoryFormatterRules(rulesToFormat))).result();
    }
}
