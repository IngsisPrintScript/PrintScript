package formatter.FormatterRules.AssignationRules;

import formatter.FormatterRules.FormatterRules;
public record SpaceBeforeAssignation() implements FormatterRules {
    @Override
    public String format() {
        return " ";
    }
}
