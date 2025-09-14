/*
 * My Project
 */

package com.ingsis.printscript.semantic.rules.operations;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.rules.SemanticRule;
import java.util.List;

public class OperationFormatSemanticRule implements SemanticRule {
    private final List<Class<? extends OperationFormatSemanticRule>> specificOperationRules;

    public OperationFormatSemanticRule(){
        specificOperationRules = List.copyOf(new ClassGraphReflectionsUtils().findSubclassesOf(OperationFormatSemanticRule.class).find());
    }

    @Override
    public boolean match(Node node) {
        try {
            for (Class<? extends OperationFormatSemanticRule> specificOperationRule :
                    specificOperationRules) {
                SemanticRule semanticRule =
                        specificOperationRule.getDeclaredConstructor().newInstance();
                if (semanticRule.match(node)) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        try {
            for (Class<? extends OperationFormatSemanticRule> specificOperationRule :
                    specificOperationRules) {
                SemanticRule semanticRule =
                        specificOperationRule.getDeclaredConstructor().newInstance();
                if (semanticRule.match(nodeToCheck)) {
                    return semanticRule.checkRules(nodeToCheck);
                }
            }
            return new IncorrectResult<>("There was no specific rule for that operator.");
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
    }
}
