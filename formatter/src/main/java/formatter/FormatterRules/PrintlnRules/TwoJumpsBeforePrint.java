package formatter.FormatterRules.PrintlnRules;

import formatter.FormatterRules.FormatterRules;

public record TwoJumpsBeforePrint() implements FormatterRules {
    @Override
    public String format() {
        return "\n\n";
    }
}
