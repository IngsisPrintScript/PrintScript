package analyzers.lexic.tokenizers.factories;

import analyzers.lexic.tokenizers.TokenizerInterface;

public interface TokenizerFactoryInterface {
    TokenizerInterface createTokenizer();
}
