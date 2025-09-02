package formatter;

import common.Node;
import formatter.FormatterRules.FactoryFormatterRules;
import formatter.FormatterRules.FormatterRules;

import java.util.HashMap;

public class Formatter {
    public String format(Node root, HashMap<FormatterRules, Boolean> rulesToFormat) {
        return root.accept(new FormatterVisitor(new FactoryFormatterRules(rulesToFormat))).result();
    }
}
