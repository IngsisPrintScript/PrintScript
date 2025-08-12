package lexer.tokenizers.factory;

import lexer.tokenizers.TokenizerInterface;
import lexer.tokenizers.ValueTokens.LiteralTokenizer;
import lexer.tokenizers.assignation.AssignationTokenizer;
import lexer.tokenizers.assignation.TypeAssignationTokenizer;
import lexer.tokenizers.eol.EndOfLineTokenizer;
import lexer.tokenizers.keyword.let.LetKeywordTokenizer;
import lexer.tokenizers.last.LastTokenizer;
import lexer.tokenizers.IdentifierTokenize.IdentifierTokenizer;
import lexer.tokenizers.type.StringTypeTokenizer;


import java.util.List;

public record DefaultTokenizerFactory() implements analyzers.lexic.tokenizers.factories.TokenizerFactoryInterface {
    @Override
    public TokenizerInterface createTokenizer() {
        TokenizerInterface tokenizer = new LastTokenizer();
        tokenizer = new LiteralTokenizer(tokenizer);
        tokenizer = new IdentifierTokenizer(tokenizer, List.of("\"", "'", ";", ":", "=", "," ,"."));
        tokenizer = new EndOfLineTokenizer(tokenizer);
        tokenizer = new TypeAssignationTokenizer(tokenizer);
        tokenizer = new AssignationTokenizer(tokenizer);
        tokenizer = new StringTypeTokenizer(tokenizer);
        tokenizer = new LetKeywordTokenizer(tokenizer);
        return tokenizer;
    }
}
