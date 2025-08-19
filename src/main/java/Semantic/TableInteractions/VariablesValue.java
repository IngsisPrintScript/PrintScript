package Semantic.TableInteractions;

import common.RulesFactory.RulesEngine;
import common.Symbol.Symbol;
import common.VariablesTable.VariablesTableInterface;
import common.nodes.Declaration.DeclarationNode;
import common.nodes.Declaration.Identifier.IdentifierNode;
import common.nodes.Declaration.TypeNode.TypeNode;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.Result;

public record VariablesValue(VariablesTableInterface variablesTable) {


    public Result addValue(DeclarationNode declaration, LiteralNode value){
        IdentifierNode id = (IdentifierNode) ((CorrectResult<?>) declaration.rightChild()).newObject();
        variablesTable.addValue(id.value(), new Symbol(((TypeNode) ((CorrectResult<?>) declaration.leftChild()).newObject()).value(),value));
        return new CorrectResult<>(variablesTable);
    }

    public Result addOnlyVariable(DeclarationNode declaration){
        IdentifierNode id = (IdentifierNode) ((CorrectResult<?>) declaration.rightChild()).newObject();
        variablesTable.addValue(id.value(),new Symbol(((TypeNode) ((CorrectResult<?>) declaration.leftChild()).newObject()).value(), null));
        return new CorrectResult<>(variablesTable);
    }
}
