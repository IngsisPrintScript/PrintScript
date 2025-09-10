package com.ingsis.printscript.syntactic.ast.builders.cor;

import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.FinalBuilder;
import com.ingsis.printscript.syntactic.ast.builders.expression.ExpressionBuilder;
import com.ingsis.printscript.syntactic.ast.builders.let.LetBuilder;
import com.ingsis.printscript.syntactic.ast.builders.print.PrintBuilder;

public record ChanBuilder() implements ChainBuilderInterface {
    @Override
    public ASTreeBuilderInterface createDefaultChain() {
        ASTreeBuilderInterface builder = new FinalBuilder();
        builder = new ExpressionBuilder(builder);
        builder = new LetBuilder(builder);
        builder = new PrintBuilder(builder);
        return builder;
    }
}
