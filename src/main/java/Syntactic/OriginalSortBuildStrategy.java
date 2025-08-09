package Syntactic;

import token.Token;
import token.TokenInterface;

import java.util.List;

public class OriginalSortBuildStrategy implements SortStrategy {


    @Override
    public List<TokenInterface> sort(List<TokenInterface> tokens) {
        return List.of(
                new Token("ASSIGNATION_TOKEN", "="),
                new Token("LET_KEYWORD_TOKEN", "let"),
                new Token("STRING_TYPE_TOKEN", "String"),
                new Token("IDENTIFIER_TOKEN", null),
                new Token("LITERAL_TOKEN", null),
                new Token("EOL_TOKEN",";")
        );
    }
}
