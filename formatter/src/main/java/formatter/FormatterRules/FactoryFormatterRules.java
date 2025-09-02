package formatter.FormatterRules;

import formatter.FormatterRules.AscriptionRules.ColonSpacingAfter;
import formatter.FormatterRules.AscriptionRules.ColonSpacingBefore;
import formatter.FormatterRules.AssignationRules.SpaceAfterAssignation;
import formatter.FormatterRules.AssignationRules.SpaceBeforeAssignation;
import formatter.FormatterRules.PrintlnRules.ZeroJumpsBeforePrint;

import java.util.HashMap;

public record FactoryFormatterRules(HashMap<FormatterRules, Boolean> rules) {

    public String SpaceBeforeColon(){
        return rules.get(new ColonSpacingBefore()) ? new ColonSpacingBefore().format() : "";
    }

    public String SpaceAfterColon() {
        return rules.get(new ColonSpacingAfter()) ?  new ColonSpacingAfter().format() : "";
    }

    public String SpaceAfterAssignation() {
       return rules.get(new SpaceBeforeAssignation())  ? new SpaceAfterAssignation().format() : "";
    }

    public String SpaceBeforeAssignation() {
        return rules.get(new SpaceBeforeAssignation()) ? new SpaceBeforeAssignation().format() : "";
    }

    public String JumpsBeforePrint(){
        FormatterRules rule = new ZeroJumpsBeforePrint();
        for(FormatterRules formatterRules : rules.keySet()){
            if(rules.get(formatterRules)){
                rule = formatterRules;
            }
        }
        return rule.format() ;
    }
}
