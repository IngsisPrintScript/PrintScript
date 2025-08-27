package common;

import common.responses.CorrectResult;
import common.responses.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CompositeNode implements Node {
    protected final List<Node> children;
    public CompositeNode(Integer length) {
        children = new ArrayList<>(Collections.nCopies(length, new NilNode()));
    }

    @Override
    public Result children() {
        return new CorrectResult<>(this.children);
    }

    @Override
    public Boolean isNil(){
        return false;
    }
}
