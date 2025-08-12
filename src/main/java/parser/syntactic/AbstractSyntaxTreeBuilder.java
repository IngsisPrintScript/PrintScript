package parser.syntactic;

import common.responses.Response;


public record AbstractSyntaxTreeBuilder(syntactic.TokensRepositoryInterface repository, AbstractSyntaxNodeVisitorInterface transpilationVisitor) implements Syntactic.AbstractSyntaxTreeBuilderInterface {

    @Override
    public Response buildAbstractSyntaxTree() {
        Response getTreeResponse = repository().getTokens();
        return getTreeResponse;
    }
}
