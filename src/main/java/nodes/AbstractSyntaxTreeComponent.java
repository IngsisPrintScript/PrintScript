package nodes;

import responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

public interface AbstractSyntaxTreeComponent {
    Response addChild(AbstractSyntaxTreeComponent child);
    Response removeChild(AbstractSyntaxTreeComponent child);
    Response getChild(Integer index);
    Response accept(AbstractSyntaxNodeVisitorInterface visitor);
}
