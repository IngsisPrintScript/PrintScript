package lexical.factories.tokenizer;

import lexical.tokenizer.FinalTokenizer;
import lexical.tokenizer.TokenizerInterface;
import lexical.tokenizer.identifier.IdentifierTokenizer;
import lexical.tokenizer.type.TypeTokenizer;
import lexical.tokenizer.type.assignation.TypeAssignationTokenizer;
import lexical.tokenizer.eol.EndOfLineTokenizer;
import lexical.tokenizer.literal.LiteralTokenizer;
import lexical.tokenizer.operator.OperatorTokenizer;

public record TokenizerFactory() implements TokenizerFactoryInterface {
    @Override
    public TokenizerInterface createDefaultTokenizer() {
        TokenizerInterface tokenizer = new FinalTokenizer();
        tokenizer = new EndOfLineTokenizer(tokenizer);
        tokenizer = new LiteralTokenizer(tokenizer);
        tokenizer = new IdentifierTokenizer(tokenizer);
        tokenizer = new OperatorTokenizer(tokenizer);
        tokenizer = new TypeTokenizer(tokenizer);
        tokenizer = new TypeAssignationTokenizer(tokenizer);
        return tokenizer;
    }
}
