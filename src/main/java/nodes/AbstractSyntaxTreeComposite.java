package nodes;

import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSyntaxTreeComposite implements AbstractSyntaxTreeComponent {
    protected final List<AbstractSyntaxTreeComponent> children;

    public AbstractSyntaxTreeComposite() {
        this.children = new ArrayList<>();
    }

    public AbstractSyntaxTreeComposite(List<AbstractSyntaxTreeComponent> children) {
        this.children = children;
    }

    @Override
    public Response addChild(AbstractSyntaxTreeComponent child){
        children.add(child);
        return new CorrectResponse<String>("The new child: " + child + " has been added correctly.");
    };

    @Override
    public Response removeChild(AbstractSyntaxTreeComponent child){
        if (children.remove(child)){
            return new CorrectResponse<String>("The new child: " + child + " has been removed correctly.");
        };
        return new IncorrectResponse("The object: " + child + " was not a child.");
    }

    @Override
    public Response getChild(Integer index) {
        if (index < 0 || index >= children.size()) {
            return new IncorrectResponse("The index out of bounds: " + index);
        }
        return new CorrectResponse<AbstractSyntaxTreeComponent>(children.get(index));
    }
    @Override
    public Response getChildren() {
        return new CorrectResponse<List<AbstractSyntaxTreeComponent>>(children);
    }
}
