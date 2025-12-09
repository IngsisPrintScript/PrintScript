
package com.ingsis.utils.rule.observer;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;

public final class JustNodeEventsChecker implements Checker {
    private final PublishersFactory publishersFactory;

    public JustNodeEventsChecker(PublishersFactory publishersFactory) {
        this.publishersFactory = publishersFactory;
    }

    @Override
    public Result<String> check(IfKeywordNode ifKeywordNode) {
        return publishersFactory.createConditionalNodePublisher().notify(ifKeywordNode);
    }

    @Override
    public Result<String> check(DeclarationKeywordNode letKeywordNode) {
        return publishersFactory.createLetNodePublisher().notify(letKeywordNode);
    }

    @Override
    public Result<String> check(ExpressionNode expressionNode) {
        return publishersFactory.createExpressionNodePublisher().notify(expressionNode);
    }
}
