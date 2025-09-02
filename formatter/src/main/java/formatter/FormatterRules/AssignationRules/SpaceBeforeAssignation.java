package formatter.FormatterRules.AssignationRules;

import formatter.FormatterRules.FormatterRules;
public class SpaceBeforeAssignation implements FormatterRules {
    @Override
    public String format() {
        return " ";
    }
}
