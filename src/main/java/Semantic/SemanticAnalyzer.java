package Semantic;

import Semantic.SemanticRules.SemanticRulesInterface;
import Semantic.SemanticVisitor.SemanticVisitor;
import common.VariablesTable.VariablesTableInterface;
import common.nodes.Node;
import common.responses.IncorrectResult;
import common.responses.Result;

public record SemanticAnalyzer(VariablesTableInterface variablesTable, SemanticRulesInterface semanticRules, Node root){

    public Result analyze(){
        if(variablesTable == null || semanticRules == null || root == null){
            return new IncorrectResult("Variables are null.");
        }
        return root.accept(new SemanticVisitor(variablesTable,semanticRules));
    }
}
