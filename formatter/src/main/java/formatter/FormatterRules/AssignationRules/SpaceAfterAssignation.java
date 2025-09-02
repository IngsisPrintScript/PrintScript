package formatter.FormatterRules.AssignationRules;

import formatter.FormatterRules.FormatterRules;

public record SpaceAfterAssignation() implements FormatterRules {
    @Override
    public String format() {
        return " ";
    }
}
