package formatter.FormatterRules.AscriptionRules;

import formatter.FormatterRules.FormatterRules;

public record ColonSpacingAfter() implements FormatterRules {
    @Override
    public String format() {
        return " ";
    }
}
