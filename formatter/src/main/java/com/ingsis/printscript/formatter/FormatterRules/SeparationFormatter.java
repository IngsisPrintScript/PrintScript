package com.ingsis.printscript.formatter.FormatterRules;

import java.util.HashMap;
import java.util.Objects;

public record SeparationFormatter(HashMap<String, Object> rules) {

    public String SpaceBeforeColon(){
        if( Objects.equals(rules.get("ColonSpacingBefore"), false)){
            return "";
        }
        return " ";
    }

    public String SpaceAfterColon() {
        if(Objects.equals(rules.get("ColonSpacingAfter"), false)){
            return "";
        }
        return " ";
    }

    public String SpaceAfterAssignation() {
        if( Objects.equals(rules.get("SpaceAfterAssignation"), false)){
            return "";
        }
        return " ";
    }

    public String SpaceBeforeAssignation() {
        if( Objects.equals(rules.get("SpaceBeforeAssignation"), false)){
            return "";
        }
        return " ";
    }

    public String JumpsBeforePrint(){
        return ("\n").repeat((Integer) rules.get("JumpsBeforePrint"));
    }
}
