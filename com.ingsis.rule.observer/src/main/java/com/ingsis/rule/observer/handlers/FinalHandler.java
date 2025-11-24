/*
 * My Project
 */

package com.ingsis.rule.observer.handlers;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;

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
