package common;

import common.responses.IncorrectResult;
import common.responses.Result;
import visitor.VisitorInterface;

public class NilNode implements Node{
    @Override
    public Result children() {
        return new IncorrectResult("Nil node has no children.");
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public Boolean isNil() {
        return true;
    }
}
