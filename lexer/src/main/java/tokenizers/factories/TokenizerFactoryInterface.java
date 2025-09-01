package tokenizers.factories;

import tokenizers.TokenizerInterface;

public interface TokenizerFactoryInterface {
    TokenizerInterface createDefaultTokenizer();
}
