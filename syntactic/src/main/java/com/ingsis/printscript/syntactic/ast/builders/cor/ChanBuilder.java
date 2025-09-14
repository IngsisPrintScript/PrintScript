/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.cor;

import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.keywords.KeywordBuilder;
import com.ingsis.printscript.syntactic.ast.builders.keywords.LetBuilder;
import com.ingsis.printscript.syntactic.ast.builders.keywords.PrintBuilder;

public record ChanBuilder() implements ChainBuilderInterface {
    @Override
    public ASTreeBuilderInterface createDefaultChain() {
        ASTreeBuilderInterface builder = new ExpressionBuilder();
        builder = new KeywordBuilder(builder);
        return builder;
    }
}
