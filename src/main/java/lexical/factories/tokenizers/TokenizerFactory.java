package lexical.factories.tokenizers;

import lexical.tokenizers.FinalTokenizer;
import lexical.tokenizers.TokenizerInterface;
import lexical.tokenizers.identifier.IdentifierTokenizer;
import lexical.tokenizers.keyword.KeywordTokenizer;
import lexical.tokenizers.punctuation.PunctuationTokenizer;
import lexical.tokenizers.type.TypeTokenizer;
import lexical.tokenizers.type.assignation.TypeAssignationTokenizer;
import lexical.tokenizers.eol.EndOfLineTokenizer;
import lexical.tokenizers.literal.LiteralTokenizer;
import lexical.tokenizers.operator.OperatorTokenizer;

public record TokenizerFactory() implements TokenizerFactoryInterface {
    @Override
    public TokenizerInterface createDefaultTokenizer() {
        TokenizerInterface tokenizer = new FinalTokenizer();
        tokenizer = new EndOfLineTokenizer(tokenizer);
        tokenizer = new LiteralTokenizer(tokenizer);
        tokenizer = new IdentifierTokenizer(tokenizer);
        tokenizer = new PunctuationTokenizer(tokenizer);
        tokenizer = new OperatorTokenizer(tokenizer);
        tokenizer = new TypeTokenizer(tokenizer);
        tokenizer = new TypeAssignationTokenizer(tokenizer);
        tokenizer = new KeywordTokenizer(tokenizer);
        return tokenizer;
    }
}
