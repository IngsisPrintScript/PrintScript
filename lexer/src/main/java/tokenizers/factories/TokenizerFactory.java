package tokenizers.factories;

import tokenizers.FinalTokenizer;
import tokenizers.TokenizerInterface;
import tokenizers.identifier.IdentifierTokenizer;
import tokenizers.keyword.KeywordTokenizer;
import tokenizers.punctuation.PunctuationTokenizer;
import tokenizers.separator.SeparatorTokenizer;
import tokenizers.type.TypeTokenizer;
import tokenizers.type.assignation.TypeAssignationTokenizer;
import tokenizers.eol.EndOfLineTokenizer;
import tokenizers.literal.LiteralTokenizer;
import tokenizers.operator.OperatorTokenizer;

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
        tokenizer = new SeparatorTokenizer(tokenizer);
        tokenizer = new KeywordTokenizer(tokenizer);
        return tokenizer;
    }
}
