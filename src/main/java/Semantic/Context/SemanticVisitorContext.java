package Semantic.Context;

import Semantic.SemanticRules.RulesCheck.CheckSemanticRules;
import Semantic.TableInteractions.VariablesValue;

public record SemanticVisitorContext(VariablesValue variablesTable, CheckSemanticRules semanticRules){}
