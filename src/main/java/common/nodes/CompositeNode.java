package common.nodes;

import common.responses.CorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeNode implements Node {
    private final List<Node> children;

    public CompositeNode() {
        this.children = new ArrayList<>();
    }

    @Override
    public Result children() {
        return new CorrectResult<>(this.children);
    }
}
