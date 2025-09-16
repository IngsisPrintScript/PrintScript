/*
 * My Project
 */

package com.ingsis.printscript.tokenizers.factories;

import com.ingsis.printscript.tokenizers.FinalTokenizer;
import com.ingsis.printscript.tokenizers.TokenizerInterface;
import com.ingsis.printscript.tokenizers.eol.EndOfLineTokenizer;
import com.ingsis.printscript.tokenizers.expressions.identifier.IdentifierTokenizer;
import com.ingsis.printscript.tokenizers.expressions.literal.LiteralTokenizer;
import com.ingsis.printscript.tokenizers.expressions.operator.OperatorTokenizer;
import com.ingsis.printscript.tokenizers.keyword.KeywordTokenizer;
import com.ingsis.printscript.tokenizers.punctuation.PunctuationTokenizer;
import com.ingsis.printscript.tokenizers.separator.SeparatorTokenizer;
import com.ingsis.printscript.tokenizers.type.TypeTokenizer;
import com.ingsis.printscript.tokenizers.type.assignation.TypeAssignationTokenizer;

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
