/*
 * My Project
 */

package com.ingsis.sca.observer.handlers;

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class FinalHandler<T extends Node> implements NodeEventHandler<T> {
    private final ResultFactory resultFactory;

    public FinalHandler(ResultFactory resultFactory) {
        this.resultFactory = resultFactory;
    }

    @Override
    public Result<String> handle(T node) {
        return resultFactory.createCorrectResult("Check passed.");
    }
}
