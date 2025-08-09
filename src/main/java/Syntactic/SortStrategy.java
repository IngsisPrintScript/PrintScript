package Syntactic;

import token.TokenInterface;

import java.util.List;

public interface SortStrategy {

    List<TokenInterface> sort(List<TokenInterface> tokens);

}
