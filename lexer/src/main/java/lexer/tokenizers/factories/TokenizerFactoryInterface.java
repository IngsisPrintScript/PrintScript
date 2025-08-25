package lexer.tokenizers.factories;

import lexer.tokenizers.TokenizerInterface;

public interface TokenizerFactoryInterface {
    TokenizerInterface createDefaultTokenizer();
}
