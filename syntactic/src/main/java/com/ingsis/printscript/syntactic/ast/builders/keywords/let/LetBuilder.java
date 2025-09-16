/*
 * My Project
 */

package com.ingsis.printscript.syntactic.ast.builders.keywords.let;

import com.ingsis.printscript.astnodes.statements.LetStatementNode;
import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.keywords.KeywordBuilder;
import com.ingsis.printscript.tokens.TokenInterface;
import com.ingsis.printscript.tokens.factories.TokenFactory;
import com.ingsis.printscript.tokens.factories.TokenFactoryInterface;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;
import java.util.Collection;
import java.util.Collections;

public class LetBuilder extends KeywordBuilder {
    private final TokenInterface LET_TOKEN_TEMPLATE;
    private final Collection<Class<? extends LetBuilder>> SUBCLASSES;

    public LetBuilder() {
        TokenFactoryInterface tokenFactory = new TokenFactory();
        LET_TOKEN_TEMPLATE = tokenFactory.createLetKeywordToken();
        Collection<Class<? extends LetBuilder>> sub =
                new ClassGraphReflectionsUtils().findSubclassesOf(LetBuilder.class).find();
        SUBCLASSES = Collections.unmodifiableCollection(sub);
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        Result<TokenInterface> peekResult = tokenStream.peek();
        if (!peekResult.isSuccessful()) return false;
        TokenInterface token = peekResult.result();
        return token.equals(LET_TOKEN_TEMPLATE);
    }

    @Override
    public Result<LetStatementNode> build(TokenStreamInterface tokenStream) {
        if (!tokenStream.consume(LET_TOKEN_TEMPLATE).isSuccessful()) {
            return new IncorrectResult<>("Stream is not a let statement");
        }
        for (Class<? extends LetBuilder> clazz : SUBCLASSES) {
            try {
                LetBuilder instance = clazz.getDeclaredConstructor().newInstance();
                Result<LetStatementNode> result = instance.build(tokenStream);
                if (result.isSuccessful()) {
                    return result;
                }
                tokenStream.resetIndex();
            } catch (RuntimeException rte) {
                throw rte;
            } catch (Exception ignored) {
            }
        }
        return new IncorrectResult<>("That let expression is not correctly build.");
    }
}
