package syntactic.ast.builders;

import common.responses.Result;
import common.tokens.stream.TokenStream;

public interface ASTreeBuilderInterface {
    Boolean canBuild(TokenStream tokenStream);
    Result build(TokenStream tokenStream);
}
