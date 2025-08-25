package Semantic.Context;

import Semantic.SemanticRules.BinaryOperations.AdditionSemanticRules;
import Semantic.SemanticRules.RulesCheck.CheckSemanticRules;
import Semantic.SemanticRules.SemanticTypeRules.NumericTypeRules;
import Semantic.SemanticRules.SemanticTypeRules.StringTypeRule;
import Semantic.TableInteractions.VariablesValue;
import common.RulesFactory.RulesEngine;
import common.Symbol.Symbol;
import common.VariablesTable.VariablesTable;

import java.util.HashMap;
import java.util.List;

public class ContextFactory {

    public SemanticVisitorContext DefaultFactory() {
        CheckSemanticRules semanticRules = new CheckSemanticRules(
                new RulesEngine(
                        List.of(new AdditionSemanticRules(), new NumericTypeRules(), new StringTypeRule())));
        VariablesValue variablesValue = new VariablesValue(new VariablesTable(new HashMap<String, Symbol>()));
        return new SemanticVisitorContext(variablesValue, semanticRules);
    }

}
