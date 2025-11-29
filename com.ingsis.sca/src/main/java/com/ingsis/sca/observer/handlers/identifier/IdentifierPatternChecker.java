/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.identifier;

import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class IdentifierPatternChecker implements NodeEventHandler<IdentifierNode> {
    private final ResultFactory resultFactory;
    private final String regEx;
    private final String patternName;

    public IdentifierPatternChecker(ResultFactory resultFactory, String regEx, String patternName) {
        this.resultFactory = resultFactory;
        this.regEx = regEx;
        this.patternName = patternName;
    }

    @Override
    public Result<String> handle(IdentifierNode node) {
        String identifierName = node.name();
        if (identifierName.matches(regEx)) {
            return resultFactory.createCorrectResult("Check passed.");
        } else {
            return resultFactory.createIncorrectResult(
                    String.format(
                            "Identifier: %s did not respected %s on line: %d and column %d",
                            patternName, identifierName, node.line(), node.column()));
        }
    }
}
