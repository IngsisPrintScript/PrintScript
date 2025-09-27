/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.variables.type;

import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.types.Types;

public class LetNodeCorrectTypeEventHandler implements NodeEventHandler<LetKeywordNode> {
    private final Runtime runtime;

    public LetNodeCorrectTypeEventHandler(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public Result<String> handle(LetKeywordNode node) {
        Types expectedType = node.typeAssignationNode().typeNode().type();
    }
}
