package formatter.FormatterRules.PrintlnRules;

import formatter.FormatterRules.FormatterRules;

public record OneJumpBeforePrint() implements FormatterRules {
    @Override
    public String format() {
        return "\n";
    }
}
