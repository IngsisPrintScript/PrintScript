/*
 * My Project
 */

package com.ingsis.utils.rule.observer.handlers;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;

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
