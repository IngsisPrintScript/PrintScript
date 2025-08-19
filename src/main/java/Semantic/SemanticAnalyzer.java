package Semantic;

import Semantic.Context.SemanticVisitorContext;
import Semantic.SemanticHandler.SemanticHandler;
import Semantic.SemanticVisitor.SemanticVisitor;
import common.nodes.Node;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.util.Map;


public record SemanticAnalyzer(SemanticVisitorContext variablesTable,
                               Map<Class<?>, SemanticHandler<?>> handlerMap, Node root){

    public Result analyze(){
        if(variablesTable == null || handlerMap == null || root == null){
            return new IncorrectResult("Variables are null.");
        }
        return root.accept(new SemanticVisitor(variablesTable,handlerMap));
    }
}
