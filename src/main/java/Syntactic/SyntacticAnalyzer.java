package Syntactic;

import AstTree.AstTree;
import responses.CorrectResponse;
import responses.Response;
import token.TokenInterface;

import java.util.List;

public class SyntacticAnalyzer<V> {

    private List<TokenInterface> tokens;
    private List<SortStrategy> sorters;
    public SyntacticAnalyzer(List<TokenInterface> tokens, List<SortStrategy> treeBuild) {
        this.tokens = tokens;
        this.sorters = treeBuild;
    }

    public AstTree<V> buildAST(){
        for(TokenInterface token : tokens){

        }

        return AstTree;
    }

}
