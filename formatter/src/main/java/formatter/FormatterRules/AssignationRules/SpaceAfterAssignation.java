package formatter.FormatterRules.AssignationRules;

import formatter.FormatterRules.FormatterRules;

public class SpaceAfterAssignation implements FormatterRules {
    @Override
    public String format() {
        return " ";
    }
}
