package formatter.FormatterRules.AscriptionRules;

import formatter.FormatterRules.FormatterRules;

public record ColonSpacingBefore() implements FormatterRules {

    public String format() {
        return " ";
    }
}
