package lexer.tokenizers.factories;

import lexer.tokenizers.FinalTokenizer;
import lexer.tokenizers.TokenizerInterface;
import lexer.tokenizers.identifier.IdentifierTokenizer;
import lexer.tokenizers.keyword.KeywordTokenizer;
import lexer.tokenizers.punctuation.PunctuationTokenizer;
import lexer.tokenizers.type.TypeTokenizer;
import lexer.tokenizers.type.assignation.TypeAssignationTokenizer;
import lexer.tokenizers.eol.EndOfLineTokenizer;
import lexer.tokenizers.literal.LiteralTokenizer;
import lexer.tokenizers.operator.OperatorTokenizer;

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
