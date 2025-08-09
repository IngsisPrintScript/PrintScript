package Syntactic;

import responses.Response;
import visitors.AbstractSyntaxNodeJavaTranspilationVisitor;
import visitors.AbstractSyntaxNodeVisitorInterface;

public record AbstractSyntaxTreeBuilder(syntactic.TokensRepositoryInterface repository, AbstractSyntaxNodeVisitorInterface transpilationVisitor) implements syntactic.AbstractSyntaxTreeBuilderInterface {

    @Override
    public Response buildAbstractSyntaxTree() {
        Response getTreeResponse = repository().getTokens();
        return getTreeResponse;
    }
}
