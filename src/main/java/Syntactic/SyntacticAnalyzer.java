package Syntactic;

import AstTree.AstTree;
import responses.CorrectResponse;
import responses.Response;
import token.TokenInterface;

import java.util.List;

public class SyntacticAnalyzer<V>{

    private final AstTree<TokenInterface> root;
    private final List<TokenInterface> token;

    public SyntacticAnalyzer(List<TokenInterface> tokens, AstTree<TokenInterface> root) {
        this.root = root;
        this.token = tokens;
    }

    public Response buildAST(){
       return
    }
}
