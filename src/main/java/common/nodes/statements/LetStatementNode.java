package common.nodes.statements;

import common.nodes.AbstractSyntaxTreeComponent;
import common.nodes.AbstractSyntaxTreeComposite;
import common.responses.CorrectResponse;
import common.responses.IncorrectResponse;
import common.responses.Response;

public class LetStatementNode extends AbstractSyntaxTreeComposite {
    @Override
    public Response accept(visitors.AbstractSyntaxNodeVisitorInterface visitor) {
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
