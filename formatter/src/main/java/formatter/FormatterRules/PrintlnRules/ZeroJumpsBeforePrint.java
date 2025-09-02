package formatter.FormatterRules.PrintlnRules;

import formatter.FormatterRules.FormatterRules;

public record ZeroJumpsBeforePrint() implements FormatterRules {
    @Override
    public String format() {
        return "";
    }
}
