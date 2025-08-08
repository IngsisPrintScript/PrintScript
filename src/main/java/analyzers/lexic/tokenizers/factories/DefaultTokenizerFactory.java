package analyzers.lexic.tokenizers.factories;

import analyzers.lexic.tokenizers.IdentifierTokenize.IdentifierTokenizer;
import analyzers.lexic.tokenizers.TokenizerInterface;
import analyzers.lexic.tokenizers.ValueTokens.LiteralTokenizer;
import analyzers.lexic.tokenizers.assignation.AssignationTokenizer;
import analyzers.lexic.tokenizers.assignation.TypeAssignationTokenizer;
import analyzers.lexic.tokenizers.eol.EndOfLineTokenizer;
import analyzers.lexic.tokenizers.keyword.let.LetKeywordTokenizer;
import analyzers.lexic.tokenizers.last.LastTokenizer;
import analyzers.lexic.tokenizers.type.StringTypeTokenizer;

import java.util.List;

public record DefaultTokenizerFactory() implements TokenizerFactoryInterface {
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
