package formatter.formattRules;

import common.Node;
import expression.binary.BinaryExpression;
import statements.LetStatementNode;

public class SpaceAfterDeclaration {

    public String format(String input, BinaryExpression node) {
        return input.replaceAll(":\\s*", ": ");
    }
}
