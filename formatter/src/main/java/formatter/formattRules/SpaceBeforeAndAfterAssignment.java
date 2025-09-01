package formatter.formattRules;

import statements.LetStatementNode;

public class SpaceBeforeAndAfterAssignment {

    public String format(String input, LetStatementNode node) {
        return input.replaceAll(":\\s*", ": ");
    }

}
