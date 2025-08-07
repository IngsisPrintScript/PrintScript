package analyzers.lexic.tokenizers.factories;

import analyzers.lexic.tokenizers.TokenizerInterface;
import analyzers.lexic.tokenizers.keyword.let.LetKeywordTokenizer;

public record DefaultTokenizerFactory() implements TokenizerFactoryInterface {
    @Override
    public TokenizerInterface createTokenizer() {
        return null;
    }
}
