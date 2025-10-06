package com.ingsis.semantic.checkers.factories;

import com.ingsis.semantic.checkers.EventsChecker;
import com.ingsis.semantic.checkers.publishers.factories.PublishersFactory;
import com.ingsis.visitors.Checker;

public class DefaultCheckerFactory implements CheckerFactory {

  @Override
  public Checker createInMemoryEventBasedChecker(PublishersFactory publishersFactory) {
    return new EventsChecker(publishersFactory);
  }

}
