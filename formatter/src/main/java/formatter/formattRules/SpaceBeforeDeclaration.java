package formatter.formattRules;

import common.Node;
import statements.LetStatementNode;

public class SpaceBeforeDeclaration {

    public String format(String input, LetStatementNode node) {
        return input.replaceAll("\\s*:", " :");
    }
}
