package formatter.FormatterRules.PrintlnRules;

import formatter.FormatterRules.FormatterRules;

public class OneJumpBeforePrint implements FormatterRules {
    @Override
    public String format() {
        return "\n";
    }
}
