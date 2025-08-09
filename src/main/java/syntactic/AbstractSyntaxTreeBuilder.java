package syntactic;

import responses.Response;
import visitors.AbstractSyntaxNodeJavaTranspilationVisitor;
import visitors.AbstractSyntaxNodeVisitorInterface;

public record AbstractSyntaxTreeBuilder(TokensRepositoryInterface repository, AbstractSyntaxNodeVisitorInterface transpilationVisitor) implements AbstractSyntaxTreeBuilderInterface {

    @Override
    public Response buildAbstractSyntaxTree() {
        Response getTreeResponse = repository().getTokens();
        return getTreeResponse;
    }
}
