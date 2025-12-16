/*
 * My Project
 */

package com.ingsis.engine.services;

import com.ingsis.charstream.factories.CharStreamFactory;
import com.ingsis.engine.versions.Version;
import com.ingsis.lexer.factories.LexerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_0;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactoryV1_1;
import com.ingsis.parser.semantic.factories.SemanticFactory;
import com.ingsis.parser.syntactic.factories.DefaultParserChainFactory;
import com.ingsis.parser.syntactic.factories.SyntacticFactory;
import com.ingsis.parser.syntactic.parsers.factory.InMemoryParserFactory;
import com.ingsis.sca.factories.ScaFactory;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.DefaultIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.LoggerIterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.status.provider.JsonRuleStatusProvider;
import com.ingsis.utils.rule.status.provider.RuleStatusProviderRegistry;
import com.ingsis.utils.rule.status.provider.YamlRuleStatusProvider;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.DefaultTokensFactory;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.template.factories.DefaultTokenTemplateFactory;
import java.io.InputStream;
import java.util.List;

public class LintService {
    public Result<String> lint(Version version, InputStream rules, InputStream in) {
        SafeIterationResult<String> iterationResult =
                createProgramLinter(version, rules).fromInputStream(in).next();
        while (iterationResult.isCorrect()) {
            iterationResult = iterationResult.nextIterator().next();
        }
        if (!iterationResult.isCorrect() && !iterationResult.error().equals("EOL")) {
            return new IncorrectResult<>(iterationResult.error());
        }
        return new CorrectResult<String>("Passed checks");
    }

    private SafeIteratorFactory<String> createProgramLinter(Version version, InputStream rules) {
        IterationResultFactory baseResultFactory = new DefaultIterationResultFactory();
        IterationResultFactory iterationResultFactory =
                new LoggerIterationResultFactory(baseResultFactory);
        SafeIteratorFactory<MetaChar> metacharIteratorFactory =
                new CharStreamFactory(iterationResultFactory);
        TokenFactory tokenFactory = new DefaultTokensFactory();
        TokenizerFactory tokenizerFactory = null;
        switch (version) {
            case V1_0 -> tokenizerFactory = new TokenizerFactoryV1_0(tokenFactory);
            case V1_1 -> tokenizerFactory = new TokenizerFactoryV1_1(tokenFactory);
        }
        SafeIteratorFactory<Token> tokenIteratorFactory =
                new LexerFactory(metacharIteratorFactory, tokenizerFactory, iterationResultFactory);
        SafeIteratorFactory<Checkable> checkableIteratorFactory =
                new SyntacticFactory(
                        tokenIteratorFactory,
                        new DefaultParserChainFactory(
                                new InMemoryParserFactory(
                                        new DefaultTokenTemplateFactory(),
                                        new DefaultNodeFactory())),
                        iterationResultFactory,
                        tokenFactory);
        SafeIteratorFactory<Interpretable> interpretableIteratorFactory =
                new SemanticFactory(checkableIteratorFactory, iterationResultFactory);
        return new ScaFactory(
                interpretableIteratorFactory,
                iterationResultFactory,
                new RuleStatusProviderRegistry(
                                List.of(new YamlRuleStatusProvider(), new JsonRuleStatusProvider()))
                        .loadRules(rules));
    }
}
