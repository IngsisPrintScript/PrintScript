package common;

import responses.IncorrectResult;
import responses.Result;

public abstract class LeafNode implements Node {
    private final String value;

    public LeafNode(String value) {
        this.value = value;
    }

    @Override
    public Result children() {
        return new IncorrectResult("Leaf nodes does not support children.");
    }

    public String value() {
        return value;
    }

    @Override
    public Boolean isNil(){
        return false;
    }
}
