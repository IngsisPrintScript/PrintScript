package com.ingsis.printscript.syntactic.ast.builders.keywords;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.reflections.ClassGraphReflectionsUtils;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.syntactic.ast.builders.ASTreeBuilderInterface;
import com.ingsis.printscript.syntactic.ast.builders.FinalBuilder;
import com.ingsis.printscript.tokens.stream.TokenStreamInterface;

import java.util.List;

public class KeywordBuilder implements ASTreeBuilderInterface {
    private final ASTreeBuilderInterface NEXT_BUILDER;
    private final List<Class<? extends KeywordBuilder>> KEYWORD_BUILDERS;

    public KeywordBuilder(ASTreeBuilderInterface nextBuilder) {
        NEXT_BUILDER = nextBuilder;
        KEYWORD_BUILDERS = List.copyOf(new ClassGraphReflectionsUtils().findSubclassesOf(KeywordBuilder.class).find());
    }

    public KeywordBuilder() {
        NEXT_BUILDER = new FinalBuilder();
        KEYWORD_BUILDERS = List.copyOf(new ClassGraphReflectionsUtils().findSubclassesOf(KeywordBuilder.class).find());
    }

    @Override
    public Boolean canBuild(TokenStreamInterface tokenStream) {
        try {
            for (Class<? extends KeywordBuilder> keywordBuilderClass : KEYWORD_BUILDERS) {
                KeywordBuilder builder = keywordBuilderClass.getDeclaredConstructor().newInstance();
                if (builder.canBuild(tokenStream)) {
                    return true;
                }
            }
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception ignored) {
            return false;
        }
        return false;
    }

    @Override
    public Result<? extends Node> build(TokenStreamInterface tokenStream) {
        try {
            for (Class<? extends KeywordBuilder> keywordBuilderClass : KEYWORD_BUILDERS) {
                KeywordBuilder builder = keywordBuilderClass.getDeclaredConstructor().newInstance();
                if (builder.canBuild(tokenStream)) {
                    return builder.build(tokenStream);
                }
            }
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            return new IncorrectResult<>(exception.getMessage());
        }
        return NEXT_BUILDER.build(tokenStream);
    }
}
