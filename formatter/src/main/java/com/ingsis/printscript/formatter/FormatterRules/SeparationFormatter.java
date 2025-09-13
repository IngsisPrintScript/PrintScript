/*
 * My Project
 */

package com.ingsis.printscript.formatter.FormatterRules;

import java.util.HashMap;
import java.util.Objects;

public record SeparationFormatter(HashMap<String, Object> rules) {

    public String spaceBeforeColon() {
        if (Objects.equals(rules.get("ColonSpacingBefore"), false)) {
            return "";
        }
        return " ";
    }

    public String spaceAfterColon() {
        if (Objects.equals(rules.get("ColonSpacingAfter"), false)) {
            return "";
        }
        return " ";
    }

    public String spaceAfterAssignation() {
        if (Objects.equals(rules.get("SpaceAfterAssignation"), false)) {
            return "";
        }
        return " ";
    }

    public String spaceBeforeAssignation() {
        if (Objects.equals(rules.get("SpaceBeforeAssignation"), false)) {
            return "";
        }
        return " ";
    }

    public String jumpsBeforePrint() {
        return ("\n").repeat((Integer) rules.get("JumpsBeforePrint"));
    }
}
