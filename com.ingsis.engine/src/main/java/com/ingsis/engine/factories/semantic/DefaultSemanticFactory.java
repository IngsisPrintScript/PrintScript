/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.factories.DefaultCheckerFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.DefaultSemanticChecker;
import com.ingsis.semantic.SemanticChecker;
import com.ingsis.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.semantic.checkers.publishers.factories.DefaultSemanticPublisherFactory;
import com.ingsis.visitors.Checker;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class DefaultSemanticFactory implements SemanticFactory {
  private final SyntacticFactory syntacticFactory;
  private final Runtime runtime;
  private final Checker checker;

  public DefaultSemanticFactory(SyntacticFactory syntacticFactory, ResultFactory resultFactory, Runtime runtime) {
    this.syntacticFactory = syntacticFactory;
    this.runtime = runtime;
    this.checker = new DefaultCheckerFactory().createInMemoryEventBasedChecker(
        new DefaultSemanticPublisherFactory(
            new DefaultHandlersFactory(runtime, resultFactory)));

  }

  @Override
  public SemanticChecker fromInputStream(InputStream in) throws IOException {
    return new DefaultSemanticChecker(
        syntacticFactory.fromInputStream(in),
        checker,
        runtime);
  }

  @Override
  public SemanticChecker fromFile(Path path) throws IOException {
    return new DefaultSemanticChecker(
        syntacticFactory.fromFile(path),
        checker,
        runtime);
  }

  @Override
  public SemanticChecker fromString(CharSequence input) throws IOException {
    return new DefaultSemanticChecker(
        syntacticFactory.fromString(input),
        checker,
        runtime);
  }
}
