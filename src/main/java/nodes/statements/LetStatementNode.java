package nodes.statements;

import nodes.AbstractSyntaxTreeComponent;
import nodes.AbstractSyntaxTreeComposite;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

public class LetStatementNode extends AbstractSyntaxTreeComposite {
    @Override
    public Response accept(AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
    public Response expression(){
        AbstractSyntaxTreeComponent expression = this.children.getFirst();
        if (expression!=null){
            return new CorrectResponse<>(expression);
        } else {
            return new IncorrectResponse("There is no expression defined for this \"let\" operation.");
        }
    }
}
