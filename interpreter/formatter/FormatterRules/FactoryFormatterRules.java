package formatter.FormatterRules;

import formatter.FormatterRules.AscriptionRules.ColonSpacingAfter;
import formatter.FormatterRules.AscriptionRules.ColonSpacingBefore;
import formatter.FormatterRules.AssignationRules.SpaceAfterAssignation;
import formatter.FormatterRules.AssignationRules.SpaceBeforeAssignation;
import formatter.FormatterRules.PrintlnRules.ZeroJumpsBeforePrint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public record FactoryFormatterRules(HashMap<FormatterRules, Boolean> rules) {

    public String SpaceBeforeColon(){
        return new ColonSpacingBefore().format();
    }

    public String SpaceAfterColon() {
        return new ColonSpacingAfter().format();
    }

    public String SpaceAfterAssignation() {
       return new SpaceAfterAssignation().format();
    }

    public String SpaceBeforeAssignation() {
        return new SpaceBeforeAssignation().format();
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
