package common.nodes;

import common.responses.IncorrectResponse;
import common.responses.Response;

public abstract class AbstractSyntaxTreeLeaf implements AbstractSyntaxTreeComponent {
    private final String value;

    public AbstractSyntaxTreeLeaf(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public Response addChild(AbstractSyntaxTreeComponent child) {
        return new IncorrectResponse("Can't add child to a leaf.");
    }
    @Override
    public Response removeChild(AbstractSyntaxTreeComponent child) {
        return new IncorrectResponse("Can't remove child to a leaf.");
    }
    @Override
    public Response getChild(Integer index) {
        return new IncorrectResponse("Can't get child from a leaf.");
    }
    @Override
    public Response getChildren() {
        return new IncorrectResponse("Can't get child from a leaf.");
    }
}
