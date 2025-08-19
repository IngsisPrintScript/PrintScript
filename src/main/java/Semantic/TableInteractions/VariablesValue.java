package Semantic.TableInteractions;

import common.Symbol.Symbol;
import common.VariablesTable.VariablesTableInterface;
import common.nodes.declaration.AscriptionNode;
import common.nodes.declaration.IdentifierNode;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.Result;

public record VariablesValue(VariablesTableInterface variablesTable) {


    public Result addValue(AscriptionNode declaration, LiteralNode value){
        IdentifierNode id = (IdentifierNode) ((CorrectResult<?>) declaration.identifier()).newObject();
        variablesTable.addValue(id.value(), new Symbol(((common.nodes.declaration.TypeNode.TypeNode) ((CorrectResult<?>) declaration.type()).newObject()).value(),value));
        return new CorrectResult<>(variablesTable);
    }

    public Result addOnlyVariable(AscriptionNode declaration){
        IdentifierNode id = (common.nodes.declaration.IdentifierNode) ((CorrectResult<?>) declaration.identifier()).newObject();
        variablesTable.addValue(id.value(),new Symbol(((common.nodes.declaration.TypeNode.TypeNode) ((CorrectResult<?>) declaration.type()).newObject()).value(), null));
        return new CorrectResult<>(variablesTable);
    }
}
