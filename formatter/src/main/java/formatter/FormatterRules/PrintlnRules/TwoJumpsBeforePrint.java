package formatter.FormatterRules.PrintlnRules;

import formatter.FormatterRules.FormatterRules;

public class TwoJumpsBeforePrint implements FormatterRules {
    @Override
    public String format() {
        return "\n\n";
    }
}
